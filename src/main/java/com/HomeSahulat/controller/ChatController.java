package com.HomeSahulat.controller;

import com.HomeSahulat.dto.ChatDto;
import com.HomeSahulat.service.ChatService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/chat/{roomId}/sendMessage")
    @SendTo("/topic/{roomId}")
    public ChatDto sendMessage(@DestinationVariable String roomId, ChatDto chatDto) throws InterruptedException {
        Thread.sleep(2000);
        // Broadcast the message to all connected clients
        return chatService.saveMessage(chatDto);
    }
}
