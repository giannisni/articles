package com.database.articles.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        BasicAuthenticationEntryPoint entryPoint = new BasicAuthenticationEntryPoint();
        entryPoint.setRealmName("My API");  // Set the realm name for the basic authentication entry point

        http
                .csrf(csrf -> csrf.disable())  // Disable CSRF protection for APIs typically accessed via token-based auth
                .authorizeHttpRequests(authorization -> authorization
                        .requestMatchers("/api/users/register").permitAll()  // Allow public access to the registration endpoint
                        .requestMatchers("/api/auth/**").permitAll()  // Allow public access to all auth-related endpoints
                        .anyRequest().authenticated())  // Require authentication for all other requests
                .httpBasic(httpBasic -> httpBasic
                        .authenticationEntryPoint(entryPoint));  // Use the configured entry point

        return http.build();
    }        // Enable HTTP Basic Authentication, simple and suitable for initial testing
        // Consider implementing logout handling if session management is used


    }

