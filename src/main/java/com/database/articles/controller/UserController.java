package com.database.articles.controller;

import com.database.articles.DTO.RegistrationDto;
import com.database.articles.model.User;
import com.database.articles.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationDto registrationDto) {
        System.out.println("Attempting to register user");
        try {
            User user = userService.registerNewUser(registrationDto);
            return ResponseEntity.ok("User registered successfully.");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
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
