package dev.rmiller.thwompchatbackend.chat;


import dev.rmiller.thwompchatbackend.security.ThwompUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    ChatRepository chatRepository;

    public ChatController(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @GetMapping
    public ResponseEntity<List<Chat>> getUserChats(Authentication authentication) {
        ThwompUserDetails userDetails = (ThwompUserDetails) authentication.getPrincipal();
        List<Chat> userChats = chatRepository.findChatsForUser(userDetails.getId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userChats);
    }


}
