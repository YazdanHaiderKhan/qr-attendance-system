package com.yazdan.qrattendance.controller;

import com.yazdan.qrattendance.dto.LoginRequest;
import com.yazdan.qrattendance.dto.LoginResponse;
import com.yazdan.qrattendance.entity.User;
import com.yazdan.qrattendance.repository.UserRepository;
import com.yazdan.qrattendance.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        if (userOpt.isEmpty()) {
            System.out.println("User not found for email: " + request.getEmail());
            return ResponseEntity.status(401).body(new LoginResponse(null, "Invalid credentials"));
        }
        User user = userOpt.get();
        boolean passwordMatches = passwordEncoder.matches(request.getPassword(), user.getPassword());
        System.out.println("Password matches: " + passwordMatches);
        if (!passwordMatches) {
            System.out.println("Provided: " + request.getPassword() + ", DB: " + user.getPassword());
            return ResponseEntity.status(401).body(new LoginResponse(null, "Invalid credentials"));
        }
        String token = jwtUtil.generateToken(user);
        return ResponseEntity.ok(new LoginResponse(token, "Login successful"));
    }
}
