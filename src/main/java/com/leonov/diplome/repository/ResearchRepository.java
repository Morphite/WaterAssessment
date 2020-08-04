package com.leonov.diplome.repository;

import com.leonov.diplome.model.Research;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ResearchRepository extends MongoRepository<Research, String> {

    Research findByName(String name);

    List<Research> findResearchByObjectId(String objectId);

    List<Research> findResearchByObjectIdAndDateTimeAfter(String objectId, LocalDateTime localDateTime);
}