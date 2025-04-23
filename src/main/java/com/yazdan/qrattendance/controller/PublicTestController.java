package com.yazdan.qrattendance.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicTestController {
    @GetMapping("/api/test/public")
    public String publicTest() {
        return "Hello";
    }

    @GetMapping("/api/test/hash")
    public String hash() {
        return new BCryptPasswordEncoder().encode("admin123");
    }
}
