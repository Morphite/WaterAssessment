package com.leonov.diplome.controller;

import com.leonov.diplome.model.Notification;
import com.leonov.diplome.service.AssessmentService;
import com.leonov.diplome.service.NotificationService;
import com.leonov.diplome.service.ObjectService;
import com.leonov.diplome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectService objectService;

    @Autowired
    private AssessmentService assessmentService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/admin-menu")
    public String adminMenu() {
        return "admin-menu";
    }

    @GetMapping("/admin-users")
    public String allUsersPage(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin-users";
    }

    @GetMapping("/admin-objects")
    public String adminObjects(Model model) {
        model.addAttribute("objects", objectService.findAll());
        return "admin-objects";
    }

    @GetMapping("/admin-assessments")
    public String assessmentList(Model model) {
        model.addAttribute("assessments", assessmentService.findAll());
        return "admin-assessments";
    }

    @GetMapping("/notifications")
    public String notifications(Model model, String filter) {
        if (filter.equals("active")) {
            model.addAttribute("notifications", notificationService.findAllUnReadOrUnReported());
            return "admin-notifications";
        } else if (filter.equals("read")) {
            model.addAttribute("notifications", notificationService.findByRead(true));
            return "admin-notifications";
        } else if (filter.equals("reported")) {
            model.addAttribute("notifications", notificationService.findByReported(true));
            return "admin-notifications";
        } else {
            model.addAttribute("notifications", notificationService.findAllReadAndReported());
            return "admin-notifications";
        }
    }

    @GetMapping("/notifications/read/{notifId}")
    public String markNotificationAsRead(@PathVariable String notifId) {
        Notification notification = notificationService.findById(notifId);
        notification.setRead(true);
        notificationService.save(notification);

        return "redirect:/notifications?filter=active";
    }

    @GetMapping("/notifications/reported/{notifId}")
    public String markNotificationAsReported(@PathVariable String notifId) {
        Notification notification = notificationService.findById(notifId);
        notification.setReported(true);
        notificationService.save(notification);

        return "redirect:/notifications";
    }
}