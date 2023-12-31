package com.iuh.simplechatapp.service;

import com.iuh.simplechatapp.model.Participant;
import com.iuh.simplechatapp.model.User;

public interface UserService {
    User findById(String id);

    User updateUserInboxes(Participant participant, String inboxId);
}