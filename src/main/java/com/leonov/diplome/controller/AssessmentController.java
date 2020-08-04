package com.leonov.diplome.controller;

import com.leonov.diplome.assessment.AssessmentModule;
import com.leonov.diplome.model.Assessment;
import com.leonov.diplome.service.AssessmentService;
import com.leonov.diplome.service.ResearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AssessmentController {

    @Autowired
    private AssessmentService assessmentService;

    @GetMapping("/assessment")
    public String quickAssessmentPage() {
        return "assessment";
    }

    @GetMapping("/assessment-menu")
    public String assessmentMenu() {
        return "assessment-menu";
    }

    @PostMapping("/assessment")
    public String quickAssessment(Assessment assessment, Model model) {
        assessmentService.quickAssessment(assessment, model);
        return "assessment-result";
    }

    @GetMapping("/assessment-list")
    public String assessmentList(Model model) {
        model.addAttribute("assessments", assessmentService.findAll());
        return "assessment-list";
    }

    @GetMapping("/assessment/details/{assessId}")
    public String assessmentDetails(@PathVariable String assessId, Model model) {
        Assessment assessment = assessmentService.findById(assessId);

        AssessmentModule.getDescriptionForParamClasses(assessment, model);

        model.addAttribute("assessment", assessment);
        return "assessment-result";
    }

    @GetMapping("/obj-assessment/{objId}")
    public String objectAssessment(@PathVariable String objId, Model model) {
        Assessment assessment = assessmentService.getObjAssessment(objId, model);

        assessmentService.save(assessment);

        model.addAttribute("assessment", assessment);
        return "assessment-result";
    }

    @PostMapping("/assessments/delete")
    public String deleteAssessmentByAdmin(String assessmentId) {
        assessmentService.delete(assessmentId);

        return "redirect:/admin-assessments";
    }
}