package com.leonov.diplome.service;

import com.leonov.diplome.model.Object;
import org.springframework.ui.Model;

import java.util.List;

public interface ObjectService {

    void save(Object object);

    Object findByName(String name);

    Object findById(String id);

    List<Object> findAll();

    void getDataForChart(Model model, String... objects);
}