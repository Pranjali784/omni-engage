package com.example.ingestion_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ThreadPoolConfig {

    /**
     * Creates a bean for a fixed-size thread pool.
     * Spring will manage this ExecutorService, allowing it to be injected into other components.
     * This pool will contain 10 threads, ready to execute tasks concurrently.
     *
     * @return An ExecutorService instance with a fixed thread pool.
     */
    @Bean
    public ExecutorService taskExecutor(){
        // Creates a thread pool with a fixed number of 10 threads.
        // When a request comes in, it will be handed to one of these threads for processing.
        return Executors.newFixedThreadPool(10);
    }
}
