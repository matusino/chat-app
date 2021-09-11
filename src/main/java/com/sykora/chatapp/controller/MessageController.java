package com.sykora.chatapp.controller;

import com.sykora.chatapp.model.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public Message greeting(Message message) throws Exception {
        return new Message(message.getUser(), message.getMessage());
    }
}
