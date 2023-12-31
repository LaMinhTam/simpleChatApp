package com.iuh.simplechatapp.model;

import lombok.*;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class MessageInInbox {
    private String id;
    private String senderUserId;
    private String content;
    private Instant timestamp;
}
