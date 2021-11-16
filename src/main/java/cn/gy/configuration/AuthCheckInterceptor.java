package cn.gy.configuration;

import cn.gy.controller.TMVisitLogController;
import cn.gy.core.web.ResultCode;
import cn.gy.core.web.ResultGenerator;
import cn.gy.service.TMRoleMenuService;
import cn.gy.util.DevUtil;
import cn.gy.util.EnvUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by JDChen on 2019/09/11
 */
@Component
public class AuthCheckInterceptor implements HandlerInterceptor {
    @Autowired
    private Environment env;
    
    @Resource
    private TMRoleMenuService tMRoleMenuService;

	@Resource
	private TMVisitLogController tMVisitLogController;

    private static final Logger logger = LoggerFactory.getLogger(AuthCheckInterceptor.class);
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (DevUtil.isDev()) {
            return true;
        }

    	try {
    		if(handler instanceof HandlerMethod) {
        		String account = EnvUtil.getEnv().getAccount();
        		if(StringUtils.isBlank(account)) {
        			logger.warn("菜单权限检查时发现账户为空");
					response.setStatus(401);
					//设置缓冲区中使用的编码为UTF-8
					response.setCharacterEncoding("UTF-8");
					//设置l浏览器接受内容时所使用的编码方式
					response.setContentType("text/html;charset = UTF-8");
					response.getWriter().write(JSON.toJSONString(
							ResultGenerator.genFailResult(ResultCode.UNAUTHORIZED, "您没有权限访问此接口")));
        			return false;
        		}else {
            		HandlerMethod h = (HandlerMethod) handler;
            		Class c = h.getMethod().getDeclaringClass();
            		RequestMapping parentUrl = AnnotatedElementUtils.findMergedAnnotation(c, RequestMapping.class);
            		RequestMapping childUrl = h.getMethodAnnotation(RequestMapping.class);
            		String prefix = "";
            		String suffix = "";
            		if(parentUrl != null && ArrayUtils.isNotEmpty(parentUrl.value())) {
            			prefix = parentUrl.value()[0];
            		}
            		if(childUrl != null && ArrayUtils.isNotEmpty(childUrl.value())) {
            			suffix = childUrl.value()[0];
            		}
            		String method = request.getMethod().toLowerCase();
            		String url = prefix + suffix;
//            		logger.info("method is {}", method);
//            		logger.info("url is {}", url);
					boolean hasmenu = tMRoleMenuService.hasMenu(account, method, url);
					if(!hasmenu){
						logger.warn("账号[{}]没有菜单[{}]的访问权限",account,url);
						setResponseUnauthorized(response);
					}else {
						tMVisitLogController.saveVisitLog(request);
					}
					return hasmenu;
				}
        	}
		} catch (Exception e) {
			logger.error("权限验证失败",e);
			setResponseUnauthorized(response);
			return false;
		}
    	
    	return HandlerInterceptor.super.preHandle(request, response, handler);
    }

	private void setResponseUnauthorized(HttpServletResponse response) throws IOException {
		response.setStatus(ResultCode.ACCESS_DENIED.code());
		//设置缓冲区中使用的编码为UTF-8
		response.setCharacterEncoding("UTF-8");
		//设置l浏览器接受内容时所使用的编码方式
		response.setContentType("text/html;charset = UTF-8");
		response.getWriter().write(JSON.toJSONString(
				ResultGenerator.genFailResult(ResultCode.ACCESS_DENIED, "您没有权限访问此接口")));
	}

	@Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
