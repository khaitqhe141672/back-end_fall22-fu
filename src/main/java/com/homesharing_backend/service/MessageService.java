package com.homesharing_backend.service;

import com.homesharing_backend.data.entity.Message;
import org.springframework.stereotype.Service;

@Service
public interface MessageService {

    public Message receiveMessage(Message message);

    public Message recMessage(Message message);
}
