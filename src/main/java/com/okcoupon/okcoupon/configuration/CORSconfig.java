package com.okcoupon.okcoupon.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
//@EnableWebMvc
public class CORSconfig{
/*
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**");
    }
    */

    @Bean
    public CorsFilter corsFilter() {

        //create new cors configuration...
        CorsConfiguration config = new CorsConfiguration();
        //allow to get credentials in cors
        config.setAllowCredentials(true);
        //allow to get from any ip/domain
        config.addAllowedOriginPattern("*");
        //expose authorization header
        config.setExposedHeaders(List.of("Authorization"));
        //allow to get any header
        config.addAllowedHeader("*");
        //tell which VERB is allowed
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        //allow to get any route -> localhost:8080/api/coupon-> /api/coupon and implement the config
        //create new url configuration for browsers
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        //return new configuration
        return new CorsFilter(source);
    }
}

