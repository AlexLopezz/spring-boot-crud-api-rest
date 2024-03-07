package com.alexdev.springbootapirestapp.services.impl;

import com.alexdev.springbootapirestapp.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findById(Integer id) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        //...
    }

    @Override
    public User save(User user) {
        return null;
    }
}
