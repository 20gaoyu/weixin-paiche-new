package cn.gy.configuration;


import cn.gy.bean.TMErrorLog;
import cn.gy.constant.SysConstant;
import cn.gy.core.service.ServiceException;
import cn.gy.core.web.InterceptorHandle;
import cn.gy.core.web.Result;
import cn.gy.core.web.ResultCode;
import cn.gy.service.TMCustomerService;
import cn.gy.service.TMErrorLogService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Spring MVC 配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebMvcConfig.class);

    @Value("${swagger.enabled:true}")
    private Boolean swaggerEnable;

    @Resource
    private RestTemplateConfig restTemplateConfig;
    @Resource
    private InterceptorHandle interceptorHandle;
    @Resource
    private TMErrorLogService tmErrorLogService;
    @Resource
    private TMCustomerService tmCustomerService;

    @Bean
    @ConditionalOnMissingBean(InterceptorHandle.class)
    public InterceptorHandle interceptorHandle() {
        return registry -> LOGGER.info("未找到拦截器处理，使用默认拦截器");
    }

    // 使用阿里 FastJson 作为JSON MessageConverter
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        //多个序列化配置需要在同一个setSerializerFeatures
        //有setSerializerFeatures时，最后的配置会覆盖之前的所有配置
        config.setSerializerFeatures(
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullListAsEmpty);
        config.setDateFormat("yyyy-MM-dd HH:mm:ss");
        // 关闭循环引用 解决 FastJson 中 “$ref 循环引用” 的问题
        // 配置后null字段会被过滤
//        config.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect);
        // 按需配置，更多参考FastJson文档哈
        converter.setFastJsonConfig(config);
        converter.setDefaultCharset(Charset.forName("UTF-8"));
        converters.add(converter);
    }

    @Override
    public void configureHandlerExceptionResolvers(
            List<HandlerExceptionResolver> exceptionResolvers) {
        //Todo 判断实例状态，对于已过期和停止的实例不能调用接口，返回提示信息 或者新建切面完成此逻辑
        exceptionResolvers.add((request, response, handler, e) -> {
            Result result = new Result();
            TMErrorLog tmErrorLog=new TMErrorLog();
            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter formatterDay = DateTimeFormatter.ofPattern("yyyyMMdd");
            String tadayStr = formatterDay.format(currentTime);
            tmErrorLog.setAccountId((Long)request.getSession().getAttribute(SysConstant.SESSION_USER_ACCOUNT_ID));
            tmErrorLog.setAccountName((String)request.getSession().getAttribute(SysConstant.SESSION_USER_ACCOUNT));
            Object customerId=null;
            customerId=request.getSession().getAttribute(SysConstant.SESSION_USER_CUSTOMER_ID);
            if(customerId==null) {
                customerId = request.getParameter("customerId");
            }
            if(customerId==null) {
                tmErrorLog.setCustomerId(null);
                tmErrorLog.setCustomerName(null);
            }
            else{

                Long customerIdLong;
                if(customerId instanceof Long)
                {
                    customerIdLong=(Long)customerId;
                }
                else{
                    customerIdLong= Long.valueOf((String)customerId);
                }
                tmErrorLog.setCustomerId(customerIdLong);
                tmErrorLog.setCustomerName(tmCustomerService.findCustomerName(customerIdLong));
            }
            tmErrorLog.setErrorTime(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()));
            tmErrorLog.setDayId(Integer.valueOf(tadayStr));

            // 业务失败的异常，如“账号或密码错误”
            if (e instanceof ServiceException) {
                result.setCode(ResultCode.FAIL).setMessage(e.getMessage());
                tmErrorLog.setErrorCode(ResultCode.FAIL.code());
                tmErrorLog.setErrorDesc(e.getMessage());
                tmErrorLog.setErrorContent(tmErrorLogService.getStackTrace(e));
                LOGGER.info(e.getMessage());
            } else if (e instanceof NoHandlerFoundException) {
                result.setCode(ResultCode.NOT_FOUND)
                        .setMessage("接口 [" + request.getRequestURI() + "] 不存在");
                tmErrorLog.setErrorCode(ResultCode.NOT_FOUND.code());
                tmErrorLog.setErrorDesc("接口 [" + request.getRequestURI() + "] 不存在");
                tmErrorLog.setErrorContent(tmErrorLogService.getStackTrace(e));
            } else if (e instanceof MethodArgumentNotValidException) {
                // bean校验异常
                StringBuffer errorMsg = new StringBuffer();
                MethodArgumentNotValidException c = (MethodArgumentNotValidException) e;
                List<ObjectError> errors = c.getBindingResult().getAllErrors();
                for (ObjectError error : errors) {
                    errorMsg.append(error.getDefaultMessage()).append(";");
                }
                result.setCode(ResultCode.FAIL)
                        .setMessage("客户端请求不规范");
                result.setData(errorMsg);
                tmErrorLog.setErrorCode(ResultCode.FAIL.code());
                tmErrorLog.setErrorDesc("客户端请求不规范");
                tmErrorLog.setErrorContent(errorMsg.toString());
            } else if (e instanceof ConstraintViolationException) {
                // 单参数校验异常
                StringBuffer errorMsg = new StringBuffer();
                Set<ConstraintViolation<?>> errors = ((ConstraintViolationException) e)
                        .getConstraintViolations();
                for (ConstraintViolation error : errors) {
                    errorMsg.append(error.getMessage()).append(";");
                }
                result.setCode(ResultCode.FAIL)
                        .setMessage("客户端请求不规范");
                result.setData(errorMsg);
                tmErrorLog.setErrorCode(ResultCode.FAIL.code());
                tmErrorLog.setErrorDesc("客户端请求不规范");
                tmErrorLog.setErrorContent(errorMsg.toString());
            } else {
                if (e instanceof MissingServletRequestParameterException
                        || e instanceof HttpMessageNotReadableException) {
                    result.setCode(ResultCode.FAIL)
                            .setMessage("客户端请求不规范");
                    tmErrorLog.setErrorCode(ResultCode.FAIL.code());
                    tmErrorLog.setErrorDesc("客户端请求不规范");
                    tmErrorLog.setErrorContent(tmErrorLogService.getStackTrace(e));
                } else {
                    result.setCode(ResultCode.INTERNAL_SERVER_ERROR).setMessage(
                            "服务器接口内部错误，请及时联系我们");
                    tmErrorLog.setErrorCode(ResultCode.INTERNAL_SERVER_ERROR.code());
                    tmErrorLog.setErrorDesc("服务器接口内部错误，请及时联系我们");
                    tmErrorLog.setErrorContent(tmErrorLogService.getStackTrace(e));
                }
                String message;
                if (handler instanceof HandlerMethod) {
                    HandlerMethod handlerMethod = (HandlerMethod) handler;
//                    ApiLog apiLog = handlerMethod.getMethodAnnotation(ApiLog.class);
//                    if(apiLog != null){
//                        String requestId = RandomStringUtils.randomNumeric(10);
//                        //入apilog表
//                        apiLogService.createLog(request, requestId, apiLog);
//                        //入统计表
//                        statisticService.createStatistic(result,request, requestId, apiLog);
//                        CustomerApiBaseResponse apiBaseResponse = new CustomerApiBaseResponse(requestId);
//                        result.setData(apiBaseResponse);
//                    }

                    message = String.format("接口 [%s] 出现异常，方法：%s.%s，异常摘要：%s",
                            request.getRequestURI(),
                            handlerMethod.getBean().getClass().getName(),
                            handlerMethod.getMethod().getName(), e.getMessage());
                    tmErrorLog.setErrorDesc("接口出现异常");
                    tmErrorLog.setErrorContent(message);
                } else {
                    message = e.getMessage();
                    tmErrorLog.setErrorDesc(message);
                    tmErrorLog.setErrorContent(tmErrorLogService.getStackTrace(e));
                }

//                result.setData(message);
                LOGGER.error(message, e);
            }
            responseResult(response, result);
            tmErrorLogService.save(tmErrorLog);
            return new ModelAndView();
        });
    }

    // 解决跨域问题
    @Override
    public void addCorsMappings(CorsRegistry registry) {
    }

    // 添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        interceptorHandle.handle(registry);
    }

    private void responseResult(HttpServletResponse response, Result result) {
        try {
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-type", "application/json;charset=UTF-8");
            response.setStatus(HttpStatus.OK.value());
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        if (swaggerEnable) {
            registry.addRedirectViewController("/v2/api-docs",
                    "/v2/api-docs?group=restful-api");
            registry.addRedirectViewController("/swagger-resources/configuration/ui",
                    "/swagger-resources/configuration/ui");
            registry.addRedirectViewController(
                    "/swagger-resources/configuration/security",
                    "/swagger-resources/configuration/security");
            registry.addRedirectViewController("/swagger-resources",
                    "/swagger-resources");
        }
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (swaggerEnable) {
            registry.addResourceHandler("/swagger-ui.html**").addResourceLocations(
                    "classpath:/META-INF/resources/swagger-ui.html");
            registry.addResourceHandler("/webjars/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
    }

    private ClientHttpRequestFactory createFactory() {
        if (restTemplateConfig.getMaxTotalConnect() <= 0) {
            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
            factory.setConnectTimeout(restTemplateConfig.getConnectTimeout());
            factory.setReadTimeout(restTemplateConfig.getReadTimeout());
            return factory;
        }
        HttpClient httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(restTemplateConfig.getMaxTotalConnect())
                .setMaxConnPerRoute(restTemplateConfig.getMaxConnectPerRoute()).build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(
                httpClient);
        factory.setConnectTimeout(restTemplateConfig.getConnectTimeout());
        factory.setReadTimeout(restTemplateConfig.getReadTimeout());
        return factory;
    }

    @Bean
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate(this.createFactory());
        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
        // 重新设置StringHttpMessageConverter字符集为UTF-8，解决中文乱码问题
        HttpMessageConverter<?> converterTarget = null;
        for (HttpMessageConverter<?> item : converterList) {
            if (StringHttpMessageConverter.class == item.getClass()) {
                converterTarget = item;
                break;
            }
        }
        if (null != converterTarget) {
            converterList.remove(converterTarget);
        }
        converterList.add(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        // 加入FastJson转换器 根据使用情况进行操作，此段注释，默认使用jackson
        configureMessageConverters(converterList);
        return restTemplate;
    }

}
