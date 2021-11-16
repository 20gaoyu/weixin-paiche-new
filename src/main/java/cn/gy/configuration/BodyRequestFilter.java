package cn.gy.configuration;

import cn.gy.core.web.BodyReaderRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Order(1)
@WebFilter(filterName = "bodyRequestFilter", urlPatterns = "/*",asyncSupported = true)
@Slf4j
public class BodyRequestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        log.info("-------------------------------");
        if(StringUtils.contains(req.getContentType(), MediaType.APPLICATION_FORM_URLENCODED.getType())){
            BodyReaderRequestWrapper wrapper = new BodyReaderRequestWrapper(req);
            chain.doFilter(wrapper, response);
        }else {
            chain.doFilter(req, response);
        }
    }

    @Override
    public void destroy() {
    }

}
