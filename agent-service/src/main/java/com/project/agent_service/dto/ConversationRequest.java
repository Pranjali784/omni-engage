package com.project.agent_service.dto;

// This is a DTO (Data Transfer Object). It is a simple class used only to
// carry data from the incoming API request to the controller and service.
// It is not an entity and has no connection to the database.
public class ConversationRequest {
    public String customerName;
    public String message;
    public String channel;

    //No-argument constructor (required by Jackson for JSON deserialization)
    public ConversationRequest(){
    }

    //Getter and Setter
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
