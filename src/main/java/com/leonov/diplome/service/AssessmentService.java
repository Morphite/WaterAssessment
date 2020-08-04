package com.leonov.diplome.service;

import com.leonov.diplome.model.Assessment;
import com.leonov.diplome.model.Object;
import org.springframework.boot.Banner;
import org.springframework.ui.Model;

import java.util.List;

public interface AssessmentService {

    void save(Assessment assessment);

    void delete(String assessmentId);

    Assessment findByName(String name);

    Assessment findById(String id);

    List<Assessment> findAll();

    List<Assessment> findAllByObjectId(String objId);

    Assessment getAssessmentAndDescriptions(Assessment assessment, Model model);

    Assessment getObjAssessment(String objId, Model model);

    Assessment quickAssessment(Assessment assessment, Model model);
}