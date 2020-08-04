package com.leonov.diplome.service.impl;

import com.leonov.diplome.model.Research;
import com.leonov.diplome.repository.ResearchRepository;
import com.leonov.diplome.service.ResearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ResearchServiceImpl implements ResearchService {

    @Autowired
    private ResearchRepository researchRepository;

    @Override
    public void save(Research research) {
        researchRepository.save(research);
    }

    @Override
    public List<Research> findAll() {
        return researchRepository.findAll();
    }

    @Override
    public List<Research> findResearchesByObjectId(String objectId) {
        return researchRepository.findResearchByObjectId(objectId);
    }

    @Override
    public List<Research> findResearchByObjectIdAndDateTimeAfter(String objectId, LocalDateTime localDateTime) {
        return researchRepository.findResearchByObjectIdAndDateTimeAfter(objectId, localDateTime);
    }

    @Override
    public void delete(String researchId) {
        researchRepository.deleteById(researchId);
    }
}