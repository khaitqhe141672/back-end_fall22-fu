package com.homesharing_backend.service.impl;

import com.homesharing_backend.data.entity.Message;
import com.homesharing_backend.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public Message receiveMessage(Message message) {
        return message;
    }

    @Override
    public Message recMessage(Message message) {

        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private", message);
        System.out.println(message.toString());
        return message;
    }
}
