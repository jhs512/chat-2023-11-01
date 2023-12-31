package com.ll.chat20231101.domain.chat.chatMessage.dto;

import com.ll.chat20231101.domain.chat.chatMessage.entity.ChatMessage;
import lombok.Getter;

@Getter
public class ChatMessageDto {
    private long id;
    private long chatRoomId;
    private String writerName;
    private String body;

    public ChatMessageDto(ChatMessage chatMessage) {
        this.id = chatMessage.getId();
        this.chatRoomId = chatMessage.getChatRoomId();
        this.writerName = chatMessage.getWriterName();
        this.body = chatMessage.getBody();
    }
}
