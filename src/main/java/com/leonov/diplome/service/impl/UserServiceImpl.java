package com.leonov.diplome.service.impl;

import com.leonov.diplome.exception.UserAlreadyExistsException;
import com.leonov.diplome.model.User;
import com.leonov.diplome.repository.UserRepository;
import com.leonov.diplome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id).orElse(new User());
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean register(User user, Model model) {
        User userFromDatabase = userRepository.findByLogin(user.getLogin());

        if (Objects.nonNull(userFromDatabase)) {
            model.addAttribute("error", "User with login:" + user.getLogin() + " already exist!");
            return false;
        } else {
            user.setRole("USER");
            userRepository.save(user);
            return true;
        }
    }
}
