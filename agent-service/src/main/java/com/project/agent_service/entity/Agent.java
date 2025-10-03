package com.project.agent_service.entity;

import jakarta.persistence.*;

@Entity
@Table(name="agents")
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AgentStatus status;

    public Agent(){
    }

    public Agent(Long id, String name, AgentStatus status){
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public AgentStatus getStatus(){
        return status;
    }

    public void setStatus(AgentStatus status){
        this.status = status;
    }
}
