package cn.gy.core.web;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

public interface InterceptorHandle {

    void handle(InterceptorRegistry registry);

}
