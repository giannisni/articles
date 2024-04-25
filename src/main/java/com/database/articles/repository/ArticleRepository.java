package com.database.articles.repository;

import com.database.articles.model.Article;
import com.database.articles.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.xml.stream.events.Comment;

public interface ArticleRepository extends JpaRepository<Article, Long> {}
