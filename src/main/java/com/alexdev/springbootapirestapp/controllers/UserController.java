package com.alexdev.springbootapirestapp.controllers;

import com.alexdev.springbootapirestapp.models.User;
import com.alexdev.springbootapirestapp.services.impl.UserService;
import jdk.jfr.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestController
@RequestMapping("/users")
class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public List<User> getAll(){
        return userService.findAll();
    }
    @GetMapping("/{id}")
    public User getById(@PathVariable Integer id){
        return userService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User save(@RequestBody User user){
        return userService.save(user);
    }

    @PutMapping
    public User update(@RequestBody User user){
        return userService.save(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Integer id){
        userService.deleteById(id);
    }
}
