package com.project.agent_service;

import com.project.agent_service.entity.*;
import com.project.agent_service.repository.AgentRepository;
import com.project.agent_service.repository.ConversationRepository;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class DataSeeder {

    private final AgentRepository agentRepository;
    private final ConversationRepository conversationRepository;

    public DataSeeder(AgentRepository agentRepository, ConversationRepository conversationRepository) {
        this.agentRepository = agentRepository;
        this.conversationRepository = conversationRepository;
    }

    @PostConstruct
    public void seedData() {
        Random random = new Random();

        // ✅ Always seed conversations if none exist
        if (conversationRepository.count() == 0) {
            List<Channel> channels = Arrays.asList(
                    Channel.CHAT, Channel.EMAIL, Channel.PHONE_CALL,
                    Channel.INSTAGRAM, Channel.TWITTER, Channel.FACEBOOK, Channel.WHATSAPP
            );

            List<String> customers = Arrays.asList(
                    "Rohan Kumar", "Anjali Gupta", "Arjun Singh", "Neha Sharma",
                    "Vikram Patel", "Priya Das", "Aditya Mehta", "Simran Kaur",
                    "Rahul Verma", "Pooja Nair", "Aman Tiwari", "Sneha Reddy",
                    "Kunal Joshi", "Isha Malhotra", "Siddharth Jain",
                    "Meera Kapoor", "Ravi Choudhary", "Alok Yadav",
                    "Deepika Rao", "Harsh Vardhan"
            );

            List<Agent> allAgents = agentRepository.findAll();

            for (int i = 0; i < customers.size(); i++) {
                Conversation conv = new Conversation();
                conv.setCustomerName(customers.get(i));
                conv.setChannel(channels.get(random.nextInt(channels.size())));
                conv.setStatus(ConversationStatus.OPEN);

                // Create first message
                Message msg = new Message();
                msg.setSender(customers.get(i));
                msg.setContent("Complaint from " + customers.get(i) + " via " + conv.getChannel());
                msg.setConversation(conv);
                conv.getMessages().add(msg);

                // Assign random agent if available
                if (!allAgents.isEmpty()) {
                    Agent assignedAgent = allAgents.get(random.nextInt(allAgents.size()));
                    conv.setAgent(assignedAgent);
                }

                conversationRepository.save(conv);
            }

            System.out.println("✅ Seeded 20 Conversations with random agents & channels");
        }
    }
}
