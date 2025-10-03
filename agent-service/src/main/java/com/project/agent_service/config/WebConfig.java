package com.project.agent_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
    /**
     * Configures Cross-Origin Resource Sharing (CORS) for the application.
     * This is a critical security feature that controls which external domains
     * are allowed to make API calls to this service.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry){
        // This configuration allows the React development server (running on localhost:3000)
        // to make requests to any endpoint in this API.
        registry.addMapping("/api/**") // Apply CORS to all endpoints under /api/
                .allowedOrigins("http://localhost:3000") // Allow requests from the React app's origin
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH") // Allow these HTTP methods
                .allowedHeaders("*") // Allow all headers
                .allowCredentials(true); // Allow cookies and authentication headers
    }
}
