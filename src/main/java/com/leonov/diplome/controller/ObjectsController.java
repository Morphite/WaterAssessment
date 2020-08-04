package com.leonov.diplome.controller;

import com.leonov.diplome.model.Assessment;
import com.leonov.diplome.model.Object;
import com.leonov.diplome.model.Point;
import com.leonov.diplome.model.Research;
import com.leonov.diplome.service.AssessmentService;
import com.leonov.diplome.service.ObjectService;
import com.leonov.diplome.service.ResearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ObjectsController {

    @Autowired
    private ObjectService objectService;

    @Autowired
    private ResearchService researchService;

    @Autowired
    private AssessmentService assessmentService;

    private final static String VIEW_CREATE_UPDATE_OBJECT_FORM = "createOrUpdateObjectForm";

    @GetMapping("/chart-menu")
    public String getChartMenu(Model model) {
        List<Object> objects = objectService.findAll();

        model.addAttribute("objects", objects);
        return "chart-menu";
    }

    @GetMapping("/chart")
    public String charts(Model model, String... objectsIds) {
        objectService.getDataForChart(model, objectsIds);

        return "chart";
    }

    @GetMapping("/objects")
    public String objects(Model model) {
        model.addAttribute("objects", objectService.findAll());
        return "objects";
    }

    @GetMapping("/objects/{objectId}/edit")
    public String initUpdateObjectForm(@PathVariable("objectId") String objectId, Model model) {
        Object object = objectService.findById(objectId);
        model.addAttribute(object);
        return VIEW_CREATE_UPDATE_OBJECT_FORM;
    }

    @GetMapping("/objects/create")
    public String createObjectForm() {
        return VIEW_CREATE_UPDATE_OBJECT_FORM;
    }

    @PostMapping("/objects/create")
    public String createObject(Object object) {
        objectService.save(object);

        return "redirect:/objects";
    }

    @GetMapping("/objects/{objectId}")
    public String researchesByObject(@PathVariable String objectId, Model model) {
        List<Research> researchList = researchService.findResearchesByObjectId(objectId);
        Object object = objectService.findById(objectId);

        List<Assessment> assessmentList = assessmentService.findAllByObjectId(objectId);

        model.addAttribute("researches", researchList);
        model.addAttribute("assessments", assessmentList);
        model.addAttribute("object", object);
        return "object-researches-assessments";
    }


}