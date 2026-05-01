package com.college.hospital.service;

import com.college.hospital.config.JwtUtil;
import com.college.hospital.entity.User;
import com.college.hospital.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmailService emailService;


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

        User savedUser = userRepository.save(user);

        if ("ROLE_PATIENT".equals(savedUser.getRole())) {
            emailService.sendRegistrationEmail(
                    savedUser.getEmail(),
                    savedUser.getName()
            );
        }

        return savedUser;
    }
    // Login check
//    public String login(User loginRequest) {
//
//        if (loginRequest.getEmail() == null || loginRequest.getPassword() == null) {
//            return "Email or Password missing";
//        }
//
//        User user = userRepository.findByEmail(loginRequest.getEmail()).orElse(null);
//
//        if (user == null) {
//            return "User not found";
//        }
//
//        boolean passwordMatch =
//                passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
//
//        if (passwordMatch) {
//
//            if (user.getRole().equals("ROLE_DOCTOR") &&
//                    !user.getApprovalStatus().equals("APPROVED")) {
//
//                return "Doctor not approved by admin yet";
//            }
//
//            return "Login successful as " + user.getRole();
//        }
//
//        return "Invalid password";
//    }


    public Map<String, String> login(User user) {

        User dbUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(dbUser.getEmail(), dbUser.getRole());

        return Map.of(
                "token", token,
                "role", dbUser.getRole()
        );
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
