package com.iuh.simplechatapp.service.impl;

import com.iuh.simplechatapp.Repository.MessageRepository;
import com.iuh.simplechatapp.model.Message;
import com.iuh.simplechatapp.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }
}
