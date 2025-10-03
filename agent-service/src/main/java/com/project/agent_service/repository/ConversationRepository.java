package com.project.agent_service.repository;

import com.project.agent_service.entity.Agent;
import com.project.agent_service.entity.Channel;
import com.project.agent_service.entity.Conversation;
import com.project.agent_service.entity.ConversationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long>{
    //Sorting: Open conversations newest First
    List<Conversation> findByStatusOrderByCreatedAtDesc(ConversationStatus status);

    // Filtering
    List<Conversation> findByChannel(Channel channel);
    List<Conversation> findByAgent_Name(String agentName);
    List<Conversation> findByStatus(ConversationStatus status);
    List<Conversation> findAllByOrderByCreatedAtDesc();  // NEW


    // Combo filters
    List<Conversation> findByChannelAndStatus(Channel channel, ConversationStatus status);
    List<Conversation> findByAgent_NameAndStatus(String agentName, ConversationStatus status);
    List<Conversation> findByChannelAndAgent_Name(Channel channel, String agentName);
    List<Conversation> findByChannelAndAgent_NameAndStatus(Channel channel, String agentName, ConversationStatus status);

    long countByAgentAndStatus(Agent agent, ConversationStatus status);
}
