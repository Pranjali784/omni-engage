package com.example.ingestion_service.Controller;

import com.example.ingestion_service.dto.CustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;

@RestController
@RequestMapping("/api")
public class IngestionController {

    private final RestTemplate restTemplate;
    private final ExecutorService taskExecutor;

    // The RestTemplate bean from your AppConfig is automatically injected here.
    @Autowired
    public IngestionController(RestTemplate restTemplate, ExecutorService taskExecutor){
        this.restTemplate = restTemplate;
        this.taskExecutor = taskExecutor;
    }

    /**
     * (The original "slow" endpoint. It handles the request synchronously )
     * This endpoint receives the initial customer request.
     * It simulates a delay and then forwards the request to the agent-service.
     */
    @PostMapping("/ingest")
    public ResponseEntity<String> handleRequest(@RequestBody CustomerRequest request){
        // 1. Simulate a slow CRM lookup or other initial processing.
        // This is the bottleneck we will optimize later.
        try{
            Thread.sleep(200);
            String agentServiceUrl = "http://localhost:8081/api/conversations";
            restTemplate.postForEntity(agentServiceUrl, request, String.class);
            System.out.println("Processing SLOW request for: "+request.getCustomerName());
        }catch (Exception e){
            System.err.println("Error in slow endpoint: "+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Failed to process request: "+e.getMessage());
        }
        return ResponseEntity.ok("Request processed synchronously for " + request.getCustomerName());
    }

    /**
     * The new "fast" endpoint. It handles requests asynchronously.
     */
    @PostMapping("/ingest-fast")
    public ResponseEntity<String> handleRequestFast(@RequestBody CustomerRequest request){
        taskExecutor.submit(()->{
            //This entire code block is executed by a separate thread from the pool.
            try{
                Thread.sleep(200);
                String agentServiceUrl = "http://localhost:8081/api/conversations";
                restTemplate.postForEntity(agentServiceUrl, request, String.class);
                System.out.println("Processing FAST (async) request for: "+ request.getCustomerName());
            } catch(Exception e){
                System.err.println("Error processing async request: " + e.getMessage());
            }
        });
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Request accepted for asyncronous processing");
    }
}
