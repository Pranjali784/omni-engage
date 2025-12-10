package com.project.agent_service;

import com.project.agent_service.entity.*;
import com.project.agent_service.repository.AgentRepository;
import com.project.agent_service.repository.ConversationRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

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
        try {
            // If no conversations exist, seed fresh data
            if (conversationRepository.count() == 0) {
                System.out.println("🌱 Seeding conversations...");

                Random random = new Random();

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

                List<Agent> agents = agentRepository.findAll();

                for (String customer : customers) {
                    Conversation conv = new Conversation();
                    conv.setCustomerName(customer);
                    conv.setChannel(channels.get(random.nextInt(channels.size())));
                    conv.setStatus(ConversationStatus.OPEN);

                    Message msg = new Message();
                    msg.setSender(customer);
                    msg.setContent("Complaint from " + customer + " via " + conv.getChannel());
                    msg.setConversation(conv);
                    conv.getMessages().add(msg);

                    // Randomly assign an agent if available
                    if (!agents.isEmpty()) {
                        conv.setAgent(agents.get(random.nextInt(agents.size())));
                    }

                    conversationRepository.save(conv);
                }

                System.out.println("✅ Seeded 20 conversations.");
            }

        } catch (Exception e) {
            System.err.println("❌ Skipping data seeding: " + e.getMessage());
        }
    }
}
