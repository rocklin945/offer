package com.rocklin.offer.common.config;

import com.rocklin.offer.common.interceptor.JwtInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置类
 * 用于注册JWT拦截器
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                // 拦截的路径
                .addPathPatterns("/**")
                // 排除登录、注册、首页分页
                .excludePathPatterns(
                        "/user/login",
                        "/user/register",
                        "/jobInfo/list",
                        "/webInfo/member-image",
                        "/material/list",
                        "/material/get/**",
                        "/pdf/preview/**",
                        "/health/**",
                        "/doc.html",
                        "/swagger-ui/**",
                        "/v3/api-docs/**"
                );
    }
}