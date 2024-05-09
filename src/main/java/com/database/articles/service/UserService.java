package com.database.articles.service;

import com.database.articles.DTO.RegistrationDto;
import com.database.articles.model.User;
import com.database.articles.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerNewUser(RegistrationDto registrationDto) {
        User existingUser = findByUsername(registrationDto.getUsername());
        if (existingUser != null) {
            System.out.println("User already exists");
            throw new IllegalArgumentException("Username already exists");
        }

        System.out.println("Creating new user");
        User newUser = new User();
        newUser.setUsername(registrationDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        return userRepository.save(newUser);
    }




    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }


}