package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")  // 允許所有路徑
                        .allowedOrigins("http://localhost:5173")  // 你的前端網址
                        .allowedMethods("GET", "POST", "PUT", "DELETE");  // 允許的 HTTP 方法
            }
        };
    }
}
