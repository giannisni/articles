package com.database.articles.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/user")

public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";  // This should map to a login view if you are using Thymeleaf or similar
    }
}
