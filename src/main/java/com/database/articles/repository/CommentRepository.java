package com.database.articles.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.database.articles.model.Comment;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByArticleId(Long articleId);

    List<Comment> findByUserId(Long articleId);

}
