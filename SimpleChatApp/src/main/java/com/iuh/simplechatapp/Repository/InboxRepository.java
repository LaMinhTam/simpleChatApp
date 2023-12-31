package com.iuh.simplechatapp.Repository;

import com.iuh.simplechatapp.model.Inbox;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface InboxRepository extends MongoRepository<Inbox, String> {
    @Query("{'participants._id': ?0}")
    List<Inbox> findInboxesByUserId(String id);
}
