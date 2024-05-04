package com.database.articles.service;

import com.database.articles.model.User;
import com.database.articles.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Attempting to find user: " + username);  // Debugging output
        return userRepository.findByUsername(username)
                .map(user -> {
                    System.out.println("User found: " + user.getUsername());  // More debugging output
                    return new org.springframework.security.core.userdetails.User(
                            user.getUsername(),
                            user.getPassword(),
                            Collections.emptyList());  // Assuming no roles for simplicity
                })
                .orElseThrow(() -> {
                    System.out.println("User not found: " + username);  // Debugging output when user is not found
                    return new UsernameNotFoundException("User not found with username: " + username);
                });
    }
}
