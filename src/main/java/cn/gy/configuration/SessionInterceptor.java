package cn.gy.configuration;


import cn.gy.core.web.ResultCode;
import cn.gy.core.web.ResultGenerator;
import cn.gy.util.DevUtil;
import cn.gy.util.EnvUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class SessionInterceptor implements HandlerInterceptor {
    /**
     * Created by gaoyu on 2019/07/18.
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (DevUtil.isDev()) {
            return true;
        }
        if (EnvUtil.getEnv().getAccount() == null || !"1".equals(EnvUtil.getEnv().getReset())) {
            log.info("Session检查时发现账户信息为空或密码未重置");
            response.setStatus(401);
            //设置缓冲区中使用的编码为UTF-8
            response.setCharacterEncoding("UTF-8");
            //设置l浏览器接受内容时所使用的编码方式
            response.setContentType("text/html;charset = UTF-8");
            response.getWriter().write(JSON.toJSONString(
                    ResultGenerator.genFailResult(ResultCode.UNAUTHORIZED, "您没有权限访问此接口---")));
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
