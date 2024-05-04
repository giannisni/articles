package com.database.articles.repository;

import com.database.articles.model.Article;
import com.database.articles.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import javax.xml.stream.events.Comment;


public interface ArticleRepository extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {
    @Query(value = "SELECT * FROM articles WHERE to_tsvector('english', title || ' ' || abstract_text) @@ plainto_tsquery('english', :keyword)",
            countQuery = "SELECT count(*) FROM articles WHERE to_tsvector('english', title || ' ' || abstract_text) @@ plainto_tsquery('english', :keyword)",
            nativeQuery = true)
    Page<Article> findByKeywordUsingFullText(String keyword, Pageable pageable);
}