package com.database.articles.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;
@Getter
@Setter
public class ArticleDTO {
    private Long id;
    private String title;
    private String abstractText;
    private Date publicationDate;
    private Set<String> authors;
    private Set<String> tags;
    private UserDTO user;

    // Getters and setters
}