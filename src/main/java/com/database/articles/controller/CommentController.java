package com.database.articles.controller;

import com.database.articles.model.Article;
import com.database.articles.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.database.articles.model.Comment;
import com.database.articles.service.CommentService;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/articles/{articleId}/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public List<Comment> getAllCommentsByArticleId(@PathVariable Long articleId) {
        return commentService.getCommentsByArticleId(articleId);
    }


    @Autowired
    private ArticleRepository articleRepository;  // Ensure this is injected

    @PostMapping
    public Comment addComment(@PathVariable Long articleId, @RequestBody Comment comment) {
        // Retrieve a managed reference to the Article, does not hit the database for data
        Article article = articleRepository.getOne(articleId);

        // Set the article to the comment
        comment.setArticle(article);

        // Save and return the updated comment
        return commentService.saveComment(comment);
    }
    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long articleId, @PathVariable Long commentId,
                                                 @RequestBody Comment commentDetails, @RequestParam Long userId) {
        if (!commentService.isUserCommentOwner(commentId, userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Comment existingComment = commentService.getCommentById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found"));

        // Use articleRepository.getOne() to obtain a reference to the Article
        Article articleRef = articleRepository.getOne(articleId);
        existingComment.setArticle(articleRef); // Set the Article reference
        existingComment.setText(commentDetails.getText()); // Update the text of the comment

        return ResponseEntity.ok(commentService.saveComment(existingComment));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId, @RequestParam Long userId) {
        if (!commentService.isUserCommentOwner(commentId, userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }
}
