package com.database.articles.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleTestController {

    @GetMapping("/test-auth")
    public ResponseEntity<String> testAuthentication() {
        return ResponseEntity.ok("Authentication successful!");
    }
}
