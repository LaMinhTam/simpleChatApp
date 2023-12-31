package com.iuh.simplechatapp.controller;

import com.iuh.simplechatapp.model.Inbox;
import com.iuh.simplechatapp.model.User;
import com.iuh.simplechatapp.service.InboxService;
import com.iuh.simplechatapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChatRestController {
    private final UserService userService;
    private final InboxService inboxService;

    public ChatRestController(UserService userService, InboxService inboxService) {
        this.userService = userService;
        this.inboxService = inboxService;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello(){
        return new ResponseEntity<>("Hello World", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id){
        User userById = userService.findById(id);
        return new ResponseEntity<>(userById, HttpStatus.OK);
    }

    @GetMapping("/chatContext/{id}")
    public ResponseEntity<List<Inbox>> getInboxesByUserId(@PathVariable String id){
        List<Inbox> inboxes = inboxService.findInboxesByUserId(id);
        return new ResponseEntity<>(inboxes, HttpStatus.OK);
    }
}
