package com.database.articles.service;

import com.database.articles.model.Article;
import com.database.articles.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.database.articles.model.Comment;
import com.database.articles.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    // CommentService or where you manage the comment
    @Autowired
    private ArticleRepository articleRepository;




    public Comment createOrUpdateComment(Long articleId, Comment comment) {
        // Get a reference to the article
        Article article = articleRepository.getOne(articleId);

        // Set the article to the comment
        comment.setArticle(article);

        // Save the updated comment
        return commentRepository.save(comment);
    }


    public List<Comment> getCommentsByArticleId(Long articleId) {
        return commentRepository.findByArticleId(articleId);
    }

    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    public boolean isUserCommentOwner(Long commentId, Long userId) {
        return commentRepository.findById(commentId)
                .map(comment -> comment.getUserId().equals(userId))
                .orElse(false);
    }


}
