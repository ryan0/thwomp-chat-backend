package dev.rmiller.thwompchatbackend.message;

import dev.rmiller.thwompchatbackend.chat.ChatRepository;
import dev.rmiller.thwompchatbackend.security.ThwompUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;

    public MessageController(MessageRepository messageRepository, ChatRepository chatRepository) {
        this.messageRepository = messageRepository;
        this.chatRepository = chatRepository;
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<Message>> getMessagesFromChat(
            @PathVariable long chatId,
            @AuthenticationPrincipal ThwompUserDetails userDetails)
    {
        if (chatRepository.hasUser(chatId, userDetails.getId())) {
            var messages = messageRepository.findMessagesByChatId(chatId);
            return ResponseEntity.ok(messages);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/chat/{chatId}")
    public ResponseEntity<Message> addMessageToChat(
            @PathVariable long chatId,
            @RequestBody MessageNew message,
            @AuthenticationPrincipal ThwompUserDetails userDetails)
    {
        if (chatRepository.hasUser(chatId, userDetails.getId())) {
            var inserted = messageRepository.insertMessageFromUserIntoChat(
                    message.getText(), userDetails.getId(), chatId, userDetails.getUsername());
            return ResponseEntity.ok(inserted);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
