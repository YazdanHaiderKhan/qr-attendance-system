package com.yazdan.qrattendance.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicTestController {
    @GetMapping("/api/test/public")
    public String publicTest() {
        return "Hello";
    }

    @GetMapping("/api/test/hash")
    public String hash(@RequestParam String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    @GetMapping("/api/test/hashmatch")
    public String hashMatch(@RequestParam String password, @RequestParam String hash) {
        return "" + new BCryptPasswordEncoder().matches(password, hash);
    }
}
