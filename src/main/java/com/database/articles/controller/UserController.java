package com.database.articles.controller;

import com.database.articles.DTO.RegistrationDto;
import com.database.articles.model.User;
import com.database.articles.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationDto registrationDto) {
        User user = userService.registerNewUser(registrationDto);
        if (user == null) {
            return ResponseEntity.badRequest().body("Username is already taken.");
        }
        return ResponseEntity.ok("User registered successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody RegistrationDto registrationDto) {
        User user = userService.registerNewUser(registrationDto);
        if (user == null) {
            return ResponseEntity.badRequest().body("Username is already taken.");
        }
        return ResponseEntity.ok("User registered successfully.");
    }


}
