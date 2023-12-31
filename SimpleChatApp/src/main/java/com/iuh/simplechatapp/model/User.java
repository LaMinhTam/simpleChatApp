package com.iuh.simplechatapp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String username;
    private List<String> inboxIds;
}