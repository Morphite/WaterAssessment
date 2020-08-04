package com.leonov.diplome.repository;

import com.leonov.diplome.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findById(String id);

    User findByLogin(String login);
}
