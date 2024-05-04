package com.database.articles.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "app_users") // Ensure table name does not clash with SQL reserved keywords
@Getter
@Setter
@JsonIgnoreProperties(value = {}, allowSetters = true) // Ignore password for security reasons too
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    // Optional: Link to Articles for bidirectional access
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Article> articles;
}
