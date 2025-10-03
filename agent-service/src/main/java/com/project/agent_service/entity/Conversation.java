package com.project.agent_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="conversation")
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String customerName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Channel channel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConversationStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    // Establishes a relationship: one agent can have many conversations
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Agent agent;

    // This is the "parent" side of the relationship. It will be serialized.
    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Message> messages = new ArrayList<>();

    // No-argument constructor required by JPA/Jackson
    public Conversation(){
        this.createdAt = LocalDateTime.now();
    }

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public ConversationStatus getStatus() {
        return status;
    }

    public void setStatus(ConversationStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
