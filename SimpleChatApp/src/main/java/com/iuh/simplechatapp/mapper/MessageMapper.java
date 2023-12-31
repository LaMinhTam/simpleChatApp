package com.iuh.simplechatapp.mapper;

import com.iuh.simplechatapp.dtos.MessageDTO;
import com.iuh.simplechatapp.model.Message;
import com.iuh.simplechatapp.model.MessageInInbox;

public class MessageMapper {
    public static Message convertToMessage(MessageInInbox messageInInbox, String receiverInboxId) {
        return Message.builder()
                .id(messageInInbox.getId())
                .senderUserId(messageInInbox.getSenderUserId())
                .receiverInboxId(receiverInboxId)
                .content(messageInInbox.getContent())
                .timestamp(messageInInbox.getTimestamp())
                .build();
    }

    public static MessageInInbox convertToMessageInInbox(Message message) {
        return MessageInInbox.builder()
                .id(message.getId())
                .senderUserId(message.getSenderUserId())
                .content(message.getContent())
                .timestamp(message.getTimestamp())
                .build();
    }

    public static MessageInInbox convertToMessageInInbox(MessageDTO message) {
        return MessageInInbox.builder()
                .id(message.getId())
                .senderUserId(message.getSenderUserId())
                .content(message.getContent())
                .timestamp(message.getTimestamp())
                .build();
    }
}
