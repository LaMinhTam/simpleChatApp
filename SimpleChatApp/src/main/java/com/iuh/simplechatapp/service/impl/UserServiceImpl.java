package com.iuh.simplechatapp.service.impl;

import com.iuh.simplechatapp.Repository.UserRepository;
import com.iuh.simplechatapp.model.Participant;
import com.iuh.simplechatapp.model.User;
import com.iuh.simplechatapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User updateUserInboxes(Participant participant, String inboxId) {
        User user = userRepository.findById(participant.getId()).orElse(null);
        user.getInboxIds().add(inboxId);
        return userRepository.save(user);
    }
}
