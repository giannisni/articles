package com.database.articles.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String username;
    // Exclude password and articles for security and to avoid recursion
    // Getters and setters
}