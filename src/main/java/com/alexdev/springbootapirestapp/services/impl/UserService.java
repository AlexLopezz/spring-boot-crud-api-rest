package com.alexdev.springbootapirestapp.services.impl;

import com.alexdev.springbootapirestapp.models.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(Integer id);
    void deleteById(Integer id);
    User save(User user);
}
