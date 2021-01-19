package com.common.interceptor;

/**
 * Created by lego-jspx01 on 2020/05/04.
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebInterceptorConfg implements WebMvcConfigurer {


    /**
     * 自己定义的拦截器类
     * @return
     */
    @Bean
    LoginTokenInterceptor loginTokenInterceptor() {
        return new LoginTokenInterceptor();
    }

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginTokenInterceptor()).addPathPatterns("/**").excludePathPatterns("/register").excludePathPatterns("/login").excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
    }

}