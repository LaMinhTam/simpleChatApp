package com.iuh.simplechatapp.mapper;

import com.iuh.simplechatapp.dtos.MessageDTO;
import com.iuh.simplechatapp.model.Message;

public class MessageDtoMapper {
    public static Message convertToMessage(MessageDTO messageDTO) {
        return Message.builder()
                .senderUserId(messageDTO.getSenderUserId())
                .receiverInboxId(messageDTO.getReceiverInboxId())
                .content(messageDTO.getContent())
                .timestamp(messageDTO.getTimestamp())
                .build();
    }
}
