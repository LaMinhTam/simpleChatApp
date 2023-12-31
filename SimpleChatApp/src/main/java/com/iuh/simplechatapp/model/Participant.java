package com.iuh.simplechatapp.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class Participant {
    private String id;
    private String username;
}

