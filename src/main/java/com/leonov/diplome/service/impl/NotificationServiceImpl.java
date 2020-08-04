package com.leonov.diplome.service.impl;

import com.leonov.diplome.model.Assessment;
import com.leonov.diplome.model.Notification;
import com.leonov.diplome.model.Research;
import com.leonov.diplome.repository.NotificationRepository;
import com.leonov.diplome.service.AssessmentService;
import com.leonov.diplome.service.NotificationService;
import com.leonov.diplome.service.ResearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private AssessmentService assessmentService;

    @Autowired
    private ResearchService researchService;

    @Override
    public void save(Notification assessment) {
        notificationRepository.save(assessment);
    }

    @Override
    public void delete(String notificationId) {
        notificationRepository.deleteById(notificationId);
    }

    @Override
    public Notification findByName(String name) {
        return notificationRepository.findByName(name);
    }

    @Override
    public Notification findById(String id) {
        return notificationRepository.findById(id).orElse(null);
    }

    @Override
    public List<Notification> findAllReadAndReported() {
        return notificationRepository.findByReadTrueAndReportedTrue();
    }

    @Override
    public List<Notification> findAllUnReadOrUnReported() {
        return notificationRepository.findByReadFalseOrReportedFalse();
    }

    @Override
    public List<Notification> findByRead(boolean read) {
        return notificationRepository.findByRead(read);
    }

    @Override
    public List<Notification> findByReported(boolean reported) {
        return notificationRepository.findByReported(reported);
    }

    @Override
    public List<Notification> refresh() {
        List<Assessment> assessments = assessmentService.findAll();
        List<Research> researches = researchService.findAll();

        return null;
    }
}
