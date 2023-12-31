package com.iuh.simplechatapp.model;

import com.iuh.simplechatapp.enums.InboxType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
@Document(collection = "inboxes")
public class Inbox {
    @Id
    private String id;
    @Enumerated(EnumType.STRING)
    private InboxType type;
    private List<Participant> participants;
    private List<MessageInInbox> messages;
}
