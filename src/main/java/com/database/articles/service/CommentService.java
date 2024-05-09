package com.database.articles.service;

import com.database.articles.model.Comment;
import com.database.articles.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<Comment> findCommentsByArticleId(Long articleId) {
        return commentRepository.findByArticleId(articleId);
    }

    public List<Comment> findCommentsByUserId(Long userId) {
        return commentRepository.findByUserId(userId);
    }


    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }


    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    public boolean isUserCommentOwner(Long commentId, Long userId) {
        return getCommentById(commentId)
                .map(comment -> comment.getUser().getId().equals(userId))
                .orElse(false);
    }
}
