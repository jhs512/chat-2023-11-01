package com.ll.chat20231101.domain.chat.chatMessage.service;

import com.ll.chat20231101.domain.chat.chatMessage.entity.ChatMessage;
import com.ll.chat20231101.domain.chat.chatMessage.repository.ChatMessageRepository;
import com.ll.chat20231101.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;

    public RsData<ChatMessage> write(long chatRoomId, String writerName, String body) {
        ChatMessage chatMessage = ChatMessage
                .builder()
                .chatRoomId(chatRoomId)
                .writerName(writerName)
                .body(body)
                .build();

        chatMessageRepository.save(chatMessage);

        return RsData.of("S-1", "%d번 메세지가 생성되었습니다.".formatted(chatMessage.getId()), chatMessage);
    }

    public List<ChatMessage> findByChatRoomIdAndIdAfter(long roomId, long fromId) {
        return chatMessageRepository.findByChatRoomIdAndIdAfter(roomId, fromId);
    }
}
