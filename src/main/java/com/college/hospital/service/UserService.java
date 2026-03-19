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

    // Register new user
    public User registerUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        String role = user.getRole();

        if ("ADMIN".equalsIgnoreCase(role)) {
            throw new RuntimeException("You cannot register as ADMIN");
        }

        if ("DOCTOR".equalsIgnoreCase(role)) {
            user.setRole("ROLE_DOCTOR");
            user.setApprovalStatus("PENDING");
        }
        else if ("PATIENT".equalsIgnoreCase(role)) {
            user.setRole("ROLE_PATIENT");
            user.setApprovalStatus("APPROVED");
        }
        else {
            throw new RuntimeException("Invalid role selected");
        }

        return userRepository.save(user);
    }

    // Login check
    public String login(User loginRequest) {

        if (loginRequest.getEmail() == null || loginRequest.getPassword() == null) {
            return "Email or Password missing";
        }

        User user = userRepository.findByEmail(loginRequest.getEmail()).orElse(null);

        if (user == null) {
            return "User not found";
        }

        boolean passwordMatch =
                passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());

        if (passwordMatch) {

            // 🔥 Correct role check
            if (user.getRole().equals("ROLE_DOCTOR") &&
                    !user.getApprovalStatus().equals("APPROVED")) {

                return "Doctor not approved by admin yet";
            }

            return "Login successful as " + user.getRole();
        }

        return "Invalid password";
    }

    public User approveDoctor(Long id) {

        User user = userRepository.findById(id).orElse(null);

        if (user != null && user.getRole().equals("ROLE_DOCTOR")) {
            user.setApprovalStatus("APPROVED");
            return userRepository.save(user);
        }

        return null;
    }
}
