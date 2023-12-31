package com.iuh.simplechatapp.service;

import com.iuh.simplechatapp.dtos.MessageDTO;
import com.iuh.simplechatapp.model.Inbox;
import com.iuh.simplechatapp.model.Message;

import java.util.List;

public interface InboxService {
    List<Inbox> findInboxesByUserId(String id);

    void updateInbox(Message message);

    Inbox saveInbox(MessageDTO message);
}
