package com.iuh.simplechatapp.service.impl;

import com.iuh.simplechatapp.Repository.InboxRepository;
import com.iuh.simplechatapp.dtos.MessageDTO;
import com.iuh.simplechatapp.enums.InboxType;
import com.iuh.simplechatapp.mapper.MessageMapper;
import com.iuh.simplechatapp.model.Inbox;
import com.iuh.simplechatapp.model.Message;
import com.iuh.simplechatapp.model.MessageInInbox;
import com.iuh.simplechatapp.service.InboxService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InboxServiceImpl implements InboxService {
    private final InboxRepository inboxRepository;
    public InboxServiceImpl(InboxRepository inboxRepository) {
        this.inboxRepository = inboxRepository;
    }

    @Override
    public List<Inbox> findInboxesByUserId(String id) {
        return inboxRepository.findInboxesByUserId(id);
    }

    @Override
    public void updateInbox(Message message) {
        String receiverInboxId = message.getReceiverInboxId();

        Inbox inbox = inboxRepository.findById(receiverInboxId).orElse(null);

        MessageInInbox messageInInbox = MessageMapper.convertToMessageInInbox(message);
        inbox.getMessages().add(messageInInbox);

        if (inbox.getMessages().size() > 20) {
            inbox.getMessages().remove(0);
        }
        inboxRepository.save(inbox);
    }

    @Override
    public Inbox saveInbox(MessageDTO message) {
        List<MessageInInbox> messageInInboxes = new ArrayList<>();
        messageInInboxes.add(MessageMapper.convertToMessageInInbox(message));

        Inbox newInbox = Inbox.builder()
                .type(InboxType.INDIVIDUAL)
                .participants(message.getParticipants())
                .messages(messageInInboxes)
                .build();

        return inboxRepository.save(newInbox);
    }
}
