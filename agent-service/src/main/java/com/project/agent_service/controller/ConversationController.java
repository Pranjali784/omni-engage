package com.project.agent_service.controller;

import com.project.agent_service.dto.ConversationRequest;
import com.project.agent_service.dto.MessageRequest;
import com.project.agent_service.entity.Conversation;
import com.project.agent_service.entity.Message;
import com.project.agent_service.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {

    private final ConversationService conversationService;

    @Autowired
    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @PostMapping
    public ResponseEntity<Conversation> createConversation(@RequestBody ConversationRequest request) {
        // Pass the entire 'request' object to the service,
        // as the service method expects a single ConversationRequest argument.
        Conversation newConversation = conversationService.createConversation(request);

        return new ResponseEntity<>(newConversation, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/close")
    public ResponseEntity<Conversation> closeConversation(@PathVariable Long id){
        Conversation closedConversation = conversationService.closeConversation(id);
        return ResponseEntity.ok(closedConversation);
    }

    //reopen closed conversation(if needed)
    @PostMapping("/{id}/reopen")
    public ResponseEntity<Conversation> reopenConversation(@PathVariable Long id) {
        Conversation reopened = conversationService.reopenConversation(id);
        return ResponseEntity.ok(reopened);
    }

    //get all open conversations (sorted)
    @GetMapping("/open")
    public ResponseEntity<List<Conversation>> getOpenConversations(){
        return ResponseEntity.ok(conversationService.getOpenConversations());
    }

    // Filter conversations
    @GetMapping("/filter")
    public ResponseEntity<List<Conversation>> filterConversations(
            @RequestParam(required = false) String channel,
            @RequestParam(required = false) String agentName,
            @RequestParam(required = false) String status) {
        return ResponseEntity.ok(conversationService.filterConversations(channel, agentName, status));
    }

    //Add a reply to a conversation
    @PostMapping("/{id}/messages")
    public ResponseEntity<Message> addMessage(@PathVariable Long id, @RequestBody MessageRequest request){
        Message newMessage = conversationService.addMessageToConversation(id, request);
        return new ResponseEntity<>(newMessage, HttpStatus.CREATED);
    }

    // get all conversations
    @GetMapping("/all")
    public ResponseEntity<List<Conversation>> getAllConversations() {
        return ResponseEntity.ok(conversationService.getAllConversations());
    }

}