package com.leonov.diplome.controller;

import com.leonov.diplome.model.DefaultDate;
import com.leonov.diplome.model.Research;
import com.leonov.diplome.service.ObjectService;
import com.leonov.diplome.service.ResearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class ResearchController {

    @Autowired
    private ResearchService researchService;

    @Autowired
    private ObjectService objectService;

    @GetMapping("/researches")
    public String researches(Model model) {
        model.addAttribute("researches", researchService.findAll());
        return "researches";
    }

    @GetMapping("/researches/add/{objectId}")
    public String getAddResearchPage(@PathVariable String objectId, Model model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String date = LocalDateTime.now().format(formatter);

        model.addAttribute("objectId", objectId);
        model.addAttribute("maxDate", date);
        model.addAttribute("defaultDate", date);

        return "createOrUpdateResearchForm";
    }

    @PostMapping("/researches/add")
    public String addResearchPost(Research research, String objectId) {
        research.setObjectId(objectId);

        researchService.save(research);

        return "redirect:/objects/" + objectId;
    }

    @PostMapping("/researches/delete")
    public String deleteResearch(String researchId, String objectId) {
        researchService.delete(researchId);

        return "redirect:/objects/" + objectId;
    }
}