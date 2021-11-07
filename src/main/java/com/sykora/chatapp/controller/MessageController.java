package com.sykora.chatapp.controller;

import com.sykora.chatapp.model.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin()
@RestController
public class MessageController {

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public Message sendMessage(Message message, Principal principal) throws Exception {
        return new Message(principal.getName(), message.getMessage());
    }
}
