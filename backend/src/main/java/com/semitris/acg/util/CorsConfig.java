package com.semitris.acg.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 匹配所有接口
                .allowedOrigins("*") //允许前端地址，*允许全部
                .allowedMethods("GET","POST","PUT","DELETE","OPTIONS") //允许请求方法
                .allowedHeaders("*") //允许所有请求头
                .allowCredentials(false) //是否允许携带cookie凭证
                .maxAge(3600); //预检请求有效期
    }
}