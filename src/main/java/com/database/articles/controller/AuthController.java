//package com.database.articles.controller;
//
//import com.database.articles.DTO.RegistrationDto;
//import com.database.articles.model.User;
//import com.database.articles.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//
//    @Autowired
//    private UserService userService;
//
//    @PostMapping("/register")
//    public ResponseEntity<?> registerUser(@RequestBody RegistrationDto registrationDto) {
//        User newUser = userService.registerNewUser(registrationDto);
//        if (newUser == null) {
//            return ResponseEntity.badRequest().body("Username is already taken.");
//        }
//        return ResponseEntity.ok("User registered successfully.");
//    }
//}
