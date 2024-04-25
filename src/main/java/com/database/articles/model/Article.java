package com.database.articles.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String abstractText;
    private Date publicationDate;
    @ElementCollection
    private List<String> authors;
    @ElementCollection
    private List<String> tags;
}
