package com.leonov.diplome.repository;

import com.leonov.diplome.model.Assessment;
import com.leonov.diplome.model.Object;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AssessmentRepository extends MongoRepository<Assessment, String> {

    Assessment findByName(String name);

    List<Assessment> findAllByObjectIdOrderByDateTime(String objId);
}