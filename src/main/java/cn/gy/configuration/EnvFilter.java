package cn.gy.configuration;

import cn.gy.core.web.Env;
import cn.gy.util.DevUtil;
import cn.gy.util.EnvUtil;
import org.springframework.core.annotation.Order;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(1)
@WebFilter(filterName = "envFilter", urlPatterns = "/*",asyncSupported = true)
public class EnvFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        Env env = new Env(req, res);
        if (DevUtil.isDev()) {
            env.setAccount("cjdtest1");
            env.setAccountId(315665274311456527L);
            env.setCustomerId(315665274307112130L);
            env.setCustomerName("cjdtest");
            env.setApiKey("8XuxSbIntow6CbPaJOrl");
        }
        EnvUtil.setEnv(env);
        chain.doFilter(req, response);
    }

    @Override
    public void destroy() {
        EnvUtil.removeEnv();
    }

}
