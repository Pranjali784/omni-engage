package com.project.agent_service;

import com.project.agent_service.entity.*;
import com.project.agent_service.repository.AgentRepository;
import com.project.agent_service.repository.ConversationRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class DataSeeder {

    private final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private final AgentRepository agentRepository;
    private final ConversationRepository conversationRepository;
    private final DataSource dataSource;

    public DataSeeder(AgentRepository agentRepository,
                      ConversationRepository conversationRepository,
                      DataSource dataSource) {
        this.agentRepository = agentRepository;
        this.conversationRepository = conversationRepository;
        this.dataSource = dataSource;
    }

    @PostConstruct
    public void seedData() {
        // Try a lightweight DB connectivity check first
        try (Connection conn = dataSource.getConnection()) {
            if (conn == null || conn.isClosed()) {
                log.warn("DB connection not available - skipping seeding");
                return;
            }
        } catch (SQLException e) {
            log.error("Cannot connect to DB. Skipping data seeding. Reason: {}", e.getMessage());
            return;
        }

        try {
            if (conversationRepository.count() == 0) {
                log.info("No conversations found - seeding sample data...");

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

                List<Agent> allAgents = agentRepository.findAll();

                for (int i = 0; i < customers.size(); i++) {
                    Conversation conv = new Conversation();
                    conv.setCustomerName(customers.get(i));
                    conv.setChannel(channels.get(random.nextInt(channels.size())));
                    conv.setStatus(ConversationStatus.OPEN);

                    Message msg = new Message();
                    msg.setSender(customers.get(i));
                    msg.setContent("Complaint from " + customers.get(i) + " via " + conv.getChannel());
                    msg.setConversation(conv);
                    conv.getMessages().add(msg);

                    if (!allAgents.isEmpty()) {
                        Agent assignedAgent = allAgents.get(random.nextInt(allAgents.size()));
                        conv.setAgent(assignedAgent);
                    }

                    conversationRepository.save(conv);
                }

                log.info("Seeded {} conversations", customers.size());
            } else {
                log.info("Conversations already exist - skipping seeding");
            }
        } catch (Exception ex) {
            // don't let seeder errors prevent application startup
            log.error("Exception during data seeding - skipping. Reason: {}", ex.getMessage(), ex);
        }
    }
}
