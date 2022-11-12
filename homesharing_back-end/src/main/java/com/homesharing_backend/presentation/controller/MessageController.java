package com.homesharing_backend.presentation.controller;

import com.homesharing_backend.data.entity.Message;
import com.homesharing_backend.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/messaging")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Message receiveMessage(@Payload Message message) {
        return messageService.receiveMessage(message);
    }

    @MessageMapping("/private-message")
    public Message recMessage(@Payload Message message) {
        return messageService.recMessage(message);
    }
}
