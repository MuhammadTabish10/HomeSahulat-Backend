package com.HomeSahulat.service.impl;

import com.HomeSahulat.dto.ChatDto;
import com.HomeSahulat.model.Chat;
import com.HomeSahulat.repository.ChatRepository;
import com.HomeSahulat.service.ChatService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    public ChatServiceImpl(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    @Transactional
    public ChatDto saveMessage(ChatDto chatDto) {
        Chat chat = toEntity(chatDto);
        return toDto(chatRepository.save(chat));
    }

    public ChatDto toDto(Chat chat) {
        return ChatDto.builder()
                .id(chat.getId())
                .timeStamp(chat.getTimeStamp())
                .content(chat.getContent())
                .sender(chat.getSender())
                .receiver(chat.getReceiver())
                .build();
    }

    public Chat toEntity(ChatDto chatDto) {
        return Chat.builder()
                .id(chatDto.getId())
                .timeStamp(chatDto.getTimeStamp())
                .content(chatDto.getContent())
                .sender(chatDto.getSender())
                .receiver(chatDto.getReceiver())
                .build();
    }
}
