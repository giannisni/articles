package com.database.articles.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {
    private Long id;
    private String text;
    private Long articleId;
    private String articleTitle;  // You can add more fields as needed


    // Getters and Setters
}
