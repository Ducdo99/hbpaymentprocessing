package com.hbpaymentprocessing.hbpaymentprocessing.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    // This class is responsible for allowing requests from any application.

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET".trim(), "POST".trim(), "PUT".trim(), "DELETE".trim())
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowCredentials(true);
    }
}
