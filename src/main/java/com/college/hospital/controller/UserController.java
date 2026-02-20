package com.college.hospital.controller;

import com.college.hospital.entity.User;
import com.college.hospital.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {

        return userService.registerUser(user);
    }
    @PostMapping("/login")
    public String login(@RequestBody User user) {

        return userService.login(user);
    }



}
