package com.project.agent_service.dto;

public class MessageRequest {
    private String content;
    private String sender;

    //Getter and Setter

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
