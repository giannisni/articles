package com.database.articles.service;

import com.database.articles.DTO.RegistrationDto;
import com.database.articles.model.User;
import com.database.articles.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerNewUser(RegistrationDto registrationDto) {
        if (findByUsername(registrationDto.getUsername()) != null) {
            // Handle the case where username already exists
            throw new IllegalArgumentException("Username already exists");
        }
        User newUser = new User();
        newUser.setUsername(registrationDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        return userRepository.save(newUser);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

}