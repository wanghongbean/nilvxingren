package com.nlxr.server.control.config;

import com.nlxr.server.control.limit.interceptor.RequestLimiterInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web mvc配置
 *
 * @author labu
 * @date 2020/10/18
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 请求限流拦截器
     */
    @Autowired
    protected RequestLimiterInterceptor requestLimiterInterceptor;

    public WebMvcConfig() {}

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 请求限流
        registry.addInterceptor(requestLimiterInterceptor).addPathPatterns("/**");
    }

}
