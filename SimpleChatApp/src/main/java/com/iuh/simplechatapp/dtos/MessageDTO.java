package com.iuh.simplechatapp.dtos;

import com.iuh.simplechatapp.model.Participant;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class MessageDTO {
    @Id
    private String id;
    private String senderUserId;
    private String receiverInboxId;
    private String content;
    private Instant timestamp;
    private List<Participant> participants;
}