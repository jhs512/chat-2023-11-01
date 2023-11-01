package com.ll.chat20231101.domain.chat.chatMessage.controller;

import com.ll.chat20231101.domain.chat.chatMessage.dto.ChatMessageDto;
import com.ll.chat20231101.domain.chat.chatMessage.entity.ChatMessage;
import com.ll.chat20231101.domain.chat.chatMessage.service.ChatMessageService;
import com.ll.chat20231101.global.rsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
@Validated
public class ChatMessageController {
    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping("/room/{roomId}")
    public String showRoom(
            final @PathVariable String roomId,
            final Model model
    ) {
        model.addAttribute("roomId", roomId);

        return "chat/chatMessage/room";
    }

    public record WriteMessageRequestBody(@NotBlank String writerName, @NotBlank String body) {
    }

    public record WriteMessageResponseBody(long id) {
    }

    @PostMapping("/room/{roomId}/message")
    @ResponseBody
    public RsData<WriteMessageResponseBody> writeMessage(
            @PathVariable final long roomId,
            @Valid @RequestBody final WriteMessageRequestBody requestBody
    ) {
        RsData<ChatMessage> writeRs = chatMessageService.write(roomId, requestBody.writerName, requestBody.body);

        messagingTemplate.convertAndSend("/topic/chat/room/" + roomId + "/messages", writeRs);

        return writeRs.newDataOf(new WriteMessageResponseBody(writeRs.getData().getId()));
    }

    @Getter
    public static class GetMessagesResponseBody {
        private final List<ChatMessageDto> messages;

        public GetMessagesResponseBody(List<ChatMessage> messages) {
            this.messages = messages
                    .stream()
                    .map(ChatMessageDto::new)
                    .toList();
        }
    }

    @GetMapping("/room/{roomId}/messages/{fromId}")
    @ResponseBody
    public RsData<GetMessagesResponseBody> getMessages(
            @PathVariable final long roomId,
            @PathVariable final long fromId
    ) {
        List<ChatMessage> messages = chatMessageService.findByChatRoomIdAndIdAfter(roomId, fromId);

        return RsData.of("S-1", "성공", new GetMessagesResponseBody(messages));
    }
}
