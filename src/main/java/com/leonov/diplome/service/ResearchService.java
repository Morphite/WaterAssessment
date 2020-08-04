package com.leonov.diplome.service;

import com.leonov.diplome.model.Research;

import java.time.LocalDateTime;
import java.util.List;

public interface ResearchService {

    void save(Research research);

    void delete(String researchId);

    List<Research> findAll();

    List<Research> findResearchesByObjectId(String objectId);

    List<Research> findResearchByObjectIdAndDateTimeAfter(String objectId, LocalDateTime localDateTime);
}