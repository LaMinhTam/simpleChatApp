package com.iuh.simplechatapp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
@Document(collection = "messages")
public class Message {
    @Id
    private String id;
    private String senderUserId;
    private String receiverInboxId;
    private String content;
    private Instant timestamp;
}
