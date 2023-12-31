package com.iuh.simplechatapp.controller;

import com.iuh.simplechatapp.dtos.MessageDTO;
import com.iuh.simplechatapp.mapper.MessageDtoMapper;
import com.iuh.simplechatapp.model.Inbox;
import com.iuh.simplechatapp.model.Message;
import com.iuh.simplechatapp.model.Participant;
import com.iuh.simplechatapp.service.InboxService;
import com.iuh.simplechatapp.service.MessageService;
import com.iuh.simplechatapp.service.UserService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageService messageService;
    private final InboxService inboxService;
    private final UserService userService;

    public ChatController(MessageService messageService, SimpMessagingTemplate simpMessagingTemplate, InboxService inboxService, UserService userService) {
        this.messageService = messageService;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.inboxService = inboxService;
        this.userService = userService;
    }

    @MessageMapping("/first_message")
    public Message receiveFirstMessage(@Payload MessageDTO messageDTO) {
        Message message = MessageDtoMapper.convertToMessage(messageDTO);
        Message savedMessage = messageService.saveMessage(message);
        List<Participant> participants = messageDTO.getParticipants();

        messageDTO.setId(savedMessage.getId());
        Inbox inbox = inboxService.saveInbox(messageDTO);

        messageDTO.setReceiverInboxId(inbox.getId());
        savedMessage.setReceiverInboxId(inbox.getId());

        participants.forEach(participant -> {
            String inboxId = messageDTO.getReceiverInboxId();
            userService.updateUserInboxes(participant, inboxId);
        });

        participants.forEach(participant -> simpMessagingTemplate.convertAndSendToUser(participant.getId(), "/first_message", inbox));

        System.out.println(savedMessage);
        return savedMessage;
    }

    @MessageMapping("/message")
    public Message receivePrivateMessage(@Payload MessageDTO messageDTO) {
        Message message = MessageDtoMapper.convertToMessage(messageDTO);

        Message savedMessage = messageService.saveMessage(message);
        inboxService.updateInbox(savedMessage);

        List<Participant> participants = messageDTO.getParticipants();

        participants.forEach(participant -> simpMessagingTemplate.convertAndSendToUser(participant.getId(), "/message", savedMessage));

        System.out.println(savedMessage);
        return savedMessage;
    }
}
