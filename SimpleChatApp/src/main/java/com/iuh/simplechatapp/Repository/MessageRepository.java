package com.iuh.simplechatapp.Repository;

import com.iuh.simplechatapp.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message, String> {
}
