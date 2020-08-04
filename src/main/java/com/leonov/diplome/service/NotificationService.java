package com.leonov.diplome.service;

import com.leonov.diplome.model.Notification;

import java.util.List;

public interface NotificationService {

    void save(Notification assessment);

    void delete(String notificationId);

    Notification findByName(String name);

    Notification findById(String id);

    List<Notification> refresh();

    List<Notification> findAllUnReadOrUnReported();

    List<Notification> findAllReadAndReported();

    List<Notification> findByRead(boolean read);

    List<Notification> findByReported(boolean reported);

}