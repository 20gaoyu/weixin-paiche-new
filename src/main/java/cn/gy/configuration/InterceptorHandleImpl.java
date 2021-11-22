package cn.gy.configuration;

import cn.gy.core.web.InterceptorHandle;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import javax.annotation.Resource;

/**
 * Created by gaoyu on 2019/07/18.
 */

@Component
public class InterceptorHandleImpl implements InterceptorHandle {

    @Resource
    private SessionInterceptor sessionInterceptor;
    
    @Resource
    private AuthCheckInterceptor authCheckInterceptor;
    
    @Override
    public void handle(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor).addPathPatterns("/aiverify/v1/**")
//                .excludePathPatterns("/aiverify/v1/login","/aiverify/v1/logout","/aiverify/v1/tMAccount/modify",
//                		"/aiverify/v1/tMCustomer/**", "/aiverify/v1/tMRole/**","/aiverify/v1/tMMenu/**",
//                		"/aiverify/v1/tMRoleMenu/**",
//                        "/tMAccount/*/reset");
    	                .excludePathPatterns("/aiverify/v1/login","/aiverify/v1/wxquery","/aiverify/v1/wxlogin","/aiverify/v1/detail/findbyid","/aiverify/v1/detail/update",
                                "/aiverify/v1/logout","/aiverify/v1/tMAccount/modify","/aiverify/v1/wxsubmit","/aiverify/v1/wxaudit","/aiverify/v1/wxcommentquery","/aiverify/v1/wxquerydriver",
                                "/aiverify/v1/api/**", "/aiverify/v1/image/feedback/**","/aiverify/v1/image/**","/aiverify/v1/image/token/**");

    	registry.addInterceptor(authCheckInterceptor).addPathPatterns("/aiverify/v1/**").
                excludePathPatterns("/aiverify/v1/tMMenu/getDisplayMenusByRole","/**/api/**","/aiverify/v1/tMAccount/modify").
                excludePathPatterns("/aiverify/v1/login","/aiverify/v1/wxcommentquery","/aiverify/v1/wxquerydriver","/aiverify/v1/wxquery","/aiverify/v1/detail/findbyid","/aiverify/v1/detail/update","/aiverify/v1/wxlogin","/aiverify/v1/logout","/aiverify/v1/tMAccount/getCurAccount","/aiverify/v1/tMAccount/getCustomer").
                excludePathPatterns("/aiverify/v1/api/**", "/aiverify/v1/wxsubmit","/aiverify/v1/wxaudit","/aiverify/v1/image/feedback/**","/aiverify/v1/image/**","/aiverify/v1/image/token/**");

    }
}
