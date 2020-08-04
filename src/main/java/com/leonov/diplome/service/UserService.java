package com.leonov.diplome.service;

import com.leonov.diplome.model.User;
import org.springframework.ui.Model;

import java.util.List;

public interface UserService {

    User save(User user);

    User findById(String id);

    User findByLogin(String login);

    List<User> findAll();

    boolean register(User user, Model model);
}
