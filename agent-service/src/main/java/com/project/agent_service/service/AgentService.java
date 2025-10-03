package com.project.agent_service.service;

import com.project.agent_service.entity.Agent;
import com.project.agent_service.repository.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgentService {
    private final AgentRepository agentRepository;

    @Autowired
    public AgentService(AgentRepository agentRepository){
        this.agentRepository = agentRepository;
    }

    public Agent createAgent(Agent agent){
        return agentRepository.save(agent);
    }

    public List<Agent> getAllAgents(){
        return agentRepository.findAll();
    }
}
