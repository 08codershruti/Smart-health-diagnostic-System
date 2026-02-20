package com.college.hospital.service;

import com.college.hospital.entity.User;
import com.college.hospital.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // REGISTER
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    // Login check
    public String login(User loginRequest) {

        if (loginRequest.getEmail() == null || loginRequest.getPassword() == null) {
            return "Email or Password missing";
        }

        User user = userRepository.findByEmail(loginRequest.getEmail());

        if (user == null) {
            return "User not found";
        }

        boolean passwordMatch = passwordEncoder.matches(
                loginRequest.getPassword(),
                user.getPassword()
        );

        if (passwordMatch) {
            return "Login successful";
        }

        return "Invalid password";
    }
}
