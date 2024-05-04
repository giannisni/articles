package com.database.articles.service;

import com.database.articles.model.Comment;
import com.database.articles.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
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
