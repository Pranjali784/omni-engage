package com.project.agent_service.service;

import com.project.agent_service.dto.ConversationRequest;
import com.project.agent_service.dto.MessageRequest;
import com.project.agent_service.entity.*;
import com.project.agent_service.repository.AgentRepository;
import com.project.agent_service.repository.ConversationRepository;
import com.project.agent_service.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final AgentRepository agentRepository;
    private final MessageRepository messageRepository;

    @Autowired
    public ConversationService(ConversationRepository conversationRepository, AgentRepository agentRepository, MessageRepository messageRepository){
        this.conversationRepository = conversationRepository;
        this.agentRepository = agentRepository;
        this.messageRepository = messageRepository;
    }

    /**
     * Creates a new conversation, assigns it to an available agent,
     * and saves the initial message.
     */
    @Transactional
    public Conversation createConversation(ConversationRequest request){

        // Step A: Find an available agent using the NEW locking query.
        // This locks the database row for the selected agent for the duration of this transaction.
        Agent availableAgent = agentRepository.findAndLockFirstAvailable(AgentStatus.AVAILABLE.name())
                .orElseThrow(()->new IllegalStateException("No available agents found to handle the request"));

        //Step B: Immediately update the agent's status to prevent others from picking it.
        availableAgent.setStatus(AgentStatus.BUSY);
        agentRepository.save(availableAgent);

        // STEP C: Create the main conversation object
        Conversation conversation = new Conversation();
        conversation.setCustomerName(request.getCustomerName());
        conversation.setChannel(Channel.valueOf(request.getChannel().toUpperCase()));
        conversation.setStatus(ConversationStatus.OPEN);
        conversation.setAgent(availableAgent);

        // STEP D: Create the initial message for this conversation
        Message initialMessage = new Message();
        initialMessage.setContent(request.getMessage());
        initialMessage.setSender(request.getCustomerName());
        initialMessage.setConversation(conversation);

        // STEP E. Add the message to the conversation's message list
        conversation.getMessages().add(initialMessage);

        // Step F: Save the conversation to the database.
        // Because of the CascadeType.ALL setting on the relationship,
        // this single save operation will also automatically save the new message.        availableAgent.setStatus(AgentStatus.BUSY);
        return conversationRepository.save(conversation);

    }

    /**
     * Finds a conversation by its ID, closes it, and sets the assigned agent's
     * status back to AVAILABLE.
     *
     * @param conversationId The ID of the conversation to close.
     * @return The updated Conversation entity.
     */
    @Transactional
    public Conversation closeConversation(Long conversationId){
        // Step 1: Find the conversation or throw an error if it doesn't exist.
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(()->new IllegalStateException("Conversation not found with ID: "+conversationId));

        // Step 2: Get the agent associated with this conversation.
        Agent agent = conversation.getAgent();

        //Step 3: Update the Conversation's status to CLOSED and save it.
        conversation.setStatus(ConversationStatus.CLOSED);
        Conversation savedConversation = conversationRepository.save(conversation);

        // Step 4: Check if the agent has any other open conversations left.
        if(agent!=null){
            long openConversationCount = conversationRepository.countByAgentAndStatus(agent, ConversationStatus.OPEN);

            // Step 5: If there are no open conversations left, update the agent's status.
            if(openConversationCount==0) {
                agent.setStatus(AgentStatus.AVAILABLE);
                agentRepository.save(agent);
            }
        }

        // Step 5: Save and return the updated conversation.
        return savedConversation;
    }

    // Reopen conversation
    @Transactional
    public Conversation reopenConversation(Long conversationId) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new IllegalStateException("Conversation not found with ID: " + conversationId));

        if (conversation.getStatus() == ConversationStatus.OPEN) {
            throw new IllegalStateException("Conversation is already open");
        }

        conversation.setStatus(ConversationStatus.OPEN);

        Agent agent = conversation.getAgent();
        if (agent != null) {
            agent.setStatus(AgentStatus.BUSY);
            agentRepository.save(agent);
        }

        return conversationRepository.save(conversation);
    }


    //Fetch open conversations sorted newest first
    public List<Conversation> getOpenConversations(){
        return conversationRepository.findByStatusOrderByCreatedAtDesc(ConversationStatus.OPEN);
    }

    //Filter conversations by channel, agentName, status
    public List<Conversation> filterConversations(String  channel, String agentName, String status){
        if (channel != null && agentName != null && status != null) {
            return conversationRepository.findByChannelAndAgent_NameAndStatus(
                    Channel.valueOf(channel.toUpperCase()), agentName,
                    ConversationStatus.valueOf(status.toUpperCase()));
        } else if (channel != null && agentName != null) {
            return conversationRepository.findByChannelAndAgent_Name(
                    Channel.valueOf(channel.toUpperCase()), agentName);
        } else if (channel != null && status != null) {
            return conversationRepository.findByChannelAndStatus(
                    Channel.valueOf(channel.toUpperCase()),
                    ConversationStatus.valueOf(status.toUpperCase()));
        } else if (agentName != null && status != null) {
            return conversationRepository.findByAgent_NameAndStatus(agentName,
                    ConversationStatus.valueOf(status.toUpperCase()));
        } else if (channel != null) {
            return conversationRepository.findByChannel(Channel.valueOf(channel.toUpperCase()));
        } else if (agentName != null) {
            return conversationRepository.findByAgent_Name(agentName);
        } else if (status != null) {
            return conversationRepository.findByStatus(ConversationStatus.valueOf(status.toUpperCase()));
        } else {
            return conversationRepository.findAll();
        }
    }


    //Add reply to an existing conversation
    @Transactional
    public Message addMessageToConversation(Long conversationId, MessageRequest request){
        Conversation conversation = conversationRepository.findById(conversationId)
                        .orElseThrow(() -> new IllegalStateException("Conversation not found with Id:" + conversationId));

        if(conversation.getStatus()==ConversationStatus.CLOSED){
            throw new IllegalStateException("Cannot add messages to a closed conversation.");
        }

        Message message = new Message();
        message.setContent(request.getContent());
        message.setSender(request.getSender());
        message.setConversation(conversation);

        //add to conversation messages list
        conversation.getMessages().add(message);

        return messageRepository.save(message);
    }

    // fetch all (open + closed)
    public List<Conversation> getAllConversations() {
        return conversationRepository.findAllByOrderByCreatedAtDesc();
    }
}
