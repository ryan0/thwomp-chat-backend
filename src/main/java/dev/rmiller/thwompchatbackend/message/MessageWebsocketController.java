package dev.rmiller.thwompchatbackend.message;

import dev.rmiller.thwompchatbackend.chat.ChatRepository;
import dev.rmiller.thwompchatbackend.security.ThwompUserDetails;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@MessageMapping("/message")
public class MessageWebsocketController {

    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;

    public MessageWebsocketController(MessageRepository messageRepository, ChatRepository chatRepository) {
        this.messageRepository = messageRepository;
        this.chatRepository = chatRepository;
    }

    @SubscribeMapping("/chat/{chatId}")
    public String subscribeToChat(
            @DestinationVariable long chatId,
            Principal principal)
    {
        var userDetails = (ThwompUserDetails) ((Authentication) principal).getPrincipal();
        if (chatRepository.hasUser(chatId, userDetails.getId())) {
            System.out.println("subscribeToChat " + chatId);
            return "subscribed";
        } else {
            throw new AccessDeniedException("User not authorized for chat");
        }
    }

    @MessageMapping("/chat/{chatId}")
    public Message sendMessageToChat(
            @DestinationVariable long chatId,
            @Payload MessageNew message,
            Principal principal)
    {
        var userDetails = (ThwompUserDetails) ((Authentication) principal).getPrincipal();
        if (chatRepository.hasUser(chatId, userDetails.getId())) {
            return messageRepository.insertMessageFromUserIntoChat(
                    message.getText(), userDetails.getId(), chatId, userDetails.getUsername());
        } else {
            throw new AccessDeniedException("User not authorized for chat");
        }
    }

}
