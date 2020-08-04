package com.leonov.diplome.repository;

import com.leonov.diplome.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotificationRepository extends MongoRepository<Notification, String> {

    Notification findByName(String name);

    List<Notification> findByRead(boolean read);

    List<Notification> findByReported(boolean informed);

    List<Notification> findByReadFalseOrReportedFalse();

    List<Notification> findByReadTrueAndReportedTrue();
}