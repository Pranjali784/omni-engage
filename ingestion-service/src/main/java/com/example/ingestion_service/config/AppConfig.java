package com.example.ingestion_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AppConfig {
    /**
     * Provides a RestTemplate bean to the Spring application context.
     * This bean can then be injected into other components to make HTTP requests.
     * @return A new RestTemplate instance.
     */
    @Bean
    public RestTemplate restTemplate(){

        RestTemplate restTemplate = new RestTemplate();

        // This is the list of "interceptors" or "middlewares" that will be applied
        // to every request made by this RestTemplate.
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        // Create a new Basic Authentication interceptor.
        // This will add the "Authorization: Basic dXNlcjpwYXNzd29yZA==" header to every request.
        // The username is "user" and the password is "password".
        interceptors.add(new BasicAuthenticationInterceptor("user", "password"));

        restTemplate.setInterceptors(interceptors);

        return restTemplate;
    }
}
