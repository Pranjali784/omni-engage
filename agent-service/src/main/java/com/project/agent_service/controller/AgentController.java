package com.project.agent_service.controller;

import com.project.agent_service.entity.Agent;
import com.project.agent_service.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agents")
public class AgentController {

    private final AgentService agentService;

    @Autowired
    public AgentController(AgentService agentService){
        this.agentService = agentService;
    }

    @PostMapping
    public ResponseEntity<Agent> createAgent(@RequestBody Agent agent){
        Agent createdAgent = agentService.createAgent(agent);
        return new ResponseEntity<>(createdAgent, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Agent>> getAllAgents(){
        List<Agent> agents = agentService.getAllAgents();
        return ResponseEntity.ok(agents);
    }

}
