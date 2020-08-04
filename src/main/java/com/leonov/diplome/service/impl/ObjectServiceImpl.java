package com.leonov.diplome.service.impl;

import com.leonov.diplome.model.Assessment;
import com.leonov.diplome.model.Object;
import com.leonov.diplome.model.Point;
import com.leonov.diplome.repository.ObjectRepository;
import com.leonov.diplome.service.AssessmentService;
import com.leonov.diplome.service.ObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Component
public class ObjectServiceImpl implements ObjectService {

    @Autowired
    private ObjectRepository objectRepository;

    @Autowired
    private AssessmentService assessmentService;

    @Override
    public void save(Object object) {
        object.setDate(LocalDate.now());
        objectRepository.save(object);
    }

    @Override
    public Object findByName(String name) {
        return objectRepository.findByName(name);
    }

    @Override
    public Object findById(String id) {
        return objectRepository.findById(id).orElse(new Object());
    }

    @Override
    public List<Object> findAll() {
        return objectRepository.findAll();
    }

    @Override
    public void getDataForChart(Model model, String... objectsIds) {
        List<List<Point>> wholePointsList = new ArrayList<>();
        for (String id : objectsIds) {
            List<Assessment> assessmentByObject = assessmentService.findAllByObjectId(id);
            List<Point> pointList = new ArrayList<>();

            fillPointList(assessmentByObject, pointList);
            wholePointsList.add(pointList);
        }

        List<String> nameList = new ArrayList<>();
        for (String objectsId : objectsIds) {
            nameList.add(findById(objectsId).getName());
        }
        model.addAttribute("dataSeries", wholePointsList);
        model.addAttribute("names", nameList);
    }

    private void fillPointList(List<Assessment> assessmentsByObjectList, List<Point> pointList) {
        for (Assessment assessment : assessmentsByObjectList) {
            Point point = new Point(getLocalDateTimeInLong(assessment.getDateTime()), assessment.getAssessmentResult());
            pointList.add(point);
        }
    }

    private long getLocalDateTimeInLong(LocalDateTime target) {
        return target.atZone(ZoneId.of("Europe/Kiev")).toInstant().toEpochMilli();
    }

}
