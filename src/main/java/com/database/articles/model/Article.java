package com.database.articles.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "articles")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String abstractText;
    private Date publicationDate;

    @ElementCollection
    @CollectionTable(name = "article_authors", joinColumns = @JoinColumn(name = "article_id"))
    @Column(name = "author")
    private Set<String> authors = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "article_tags", joinColumns = @JoinColumn(name = "article_id"))
    @Column(name = "tag")
    private Set<String> tags = new HashSet<>();

    // Link to User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
