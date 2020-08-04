package com.leonov.diplome.service.impl;

import com.leonov.diplome.model.Assessment;
import com.leonov.diplome.model.Notification;
import com.leonov.diplome.model.Research;
import com.leonov.diplome.repository.AssessmentRepository;
import com.leonov.diplome.service.AssessmentService;
import com.leonov.diplome.service.NotificationService;
import com.leonov.diplome.service.ObjectService;
import com.leonov.diplome.service.ResearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.List;

import static com.leonov.diplome.assessment.AssessmentModule.*;

@Service
public class AssessmentServiceImpl implements AssessmentService {

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private ResearchService researchService;

    @Autowired
    private ObjectService objectService;

    @Autowired
    private NotificationService notificationService;

    @Override
    public void save(Assessment assessment) {
        assessmentRepository.save(assessment);
    }

    @Override
    public void delete(String assessmentId) {
        assessmentRepository.deleteById(assessmentId);
    }

    @Override
    public Assessment findByName(String name) {
        return assessmentRepository.findByName(name);
    }

    @Override
    public Assessment findById(String id) {
        return assessmentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Assessment> findAll() {
        return assessmentRepository.findAll();
    }

    @Override
    public List<Assessment> findAllByObjectId(String objId) {
        return assessmentRepository.findAllByObjectIdOrderByDateTime(objId);
    }

    @Override
    public Assessment getAssessmentAndDescriptions(Assessment assessment, Model model) {
        assessmentByZobkov(assessment);
        getDescriptionForParamClasses(assessment, model);

        return assessment;
    }

    @Override
    public Assessment getObjAssessment(String objId, Model model) {
        LocalDateTime monthAgo = LocalDateTime.now().minusDays(30);

        List<Research> researchList = researchService.findResearchByObjectIdAndDateTimeAfter(objId, monthAgo);

        Assessment assessment = new Assessment();
        assessment.setObjectId(objId);
        assessment.setName("Whole object assessment");
        assessment.setDateTime(LocalDateTime.now());

        getAverageFromResearches(assessment, researchList);
        getAssessmentAndDescriptions(assessment, model);

        checkForCreateNotification(assessment);

        return assessment;
    }

    @Override
    public Assessment quickAssessment(Assessment assessment, Model model) {
        getAssessmentAndDescriptions(assessment, model);
        save(assessment);
        return assessment;
    }

    private void checkForCreateNotification(Assessment assessment) {
        // part for assessment result
        double assessmentResult = assessment.getAssessmentResult();


        if (assessmentResult < 2.6) {
            Notification notification = new Notification();
            notification.setObjectId(assessment.getObjectId());
            notification.setName("Danger situation on " + objectService.findById(assessment.getObjectId()).getName());
            notification.setDateTime(LocalDateTime.now());
            notification.setNotes("URGENT! Assessment result lower than 2.6 points!");
            notification.setColor("danger");
            notificationService.save(notification);
        } else if (assessmentResult > 2.6 && assessmentResult < 3.6) {
            Notification notification = new Notification();
            notification.setObjectId(assessment.getObjectId());
            notification.setName("Check situation on " + objectService.findById(assessment.getObjectId()).getName());
            notification.setDateTime(LocalDateTime.now());
            notification.setNotes("Assessment result lower than 3.6 points! \nPay attention to it!");
            notification.setColor("warning");
            notificationService.save(notification);
        }

        // part for parameters result

    }

    private void getAverageFromResearches(Assessment assessment, List<Research> researchList) {
        double pH = 0;
        double hum = 0;
        double trophy = 0;
        double oxygen = 0;

        for (Research research : researchList) {
            pH += research.getPh();
            hum += research.getHum();
            trophy += research.getTrophy();
            oxygen += research.getOxygen();
        }
        int researchQuantity = researchList.size();

        pH /= researchQuantity;
        hum /= researchQuantity;
        trophy /= researchQuantity;
        oxygen /= researchQuantity;

        assessment.setPh(roundTo3Decimals(pH));
        assessment.setHum(roundTo3Decimals(hum));
        assessment.setTrophy(roundTo3Decimals(trophy));
        assessment.setOxygen(roundTo3Decimals(oxygen));
    }
}
