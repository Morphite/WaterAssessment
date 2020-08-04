package com.leonov.diplome.repository;

import com.leonov.diplome.model.Object;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ObjectRepository extends MongoRepository<Object, String> {

    Object findByName(String name);
}