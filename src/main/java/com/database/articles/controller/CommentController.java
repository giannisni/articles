package com.database.articles.controller;

import com.database.articles.DTO.CommentDTO;
import com.database.articles.model.Article;
import com.database.articles.model.Comment;
import com.database.articles.model.User;
import com.database.articles.service.ArticleService;
import com.database.articles.service.CommentService;
import com.database.articles.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;
    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody CommentDTO commentDTO, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        Article article = articleService.getArticleById(commentDTO.getArticleId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Article not found"));

        Comment comment = new Comment();
        comment.setText(commentDTO.getText());
        comment.setArticle(article);
        comment.setUser(user);

        Comment savedComment = commentService.saveComment(comment);
        return ResponseEntity.ok(convertToDTO(savedComment));
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id, @RequestBody Comment commentDetails, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return commentService.getCommentById(id)
                .map(comment -> {
                    if (!comment.getUser().getUsername().equals(userDetails.getUsername())) {
                        // Return ResponseEntity with forbidden status when user is not the owner of the comment
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                    }
                    // Update the comment fields
                    comment.setText(commentDetails.getText());
                    Comment updatedComment = commentService.saveComment(comment);
                    // Return the updated comment wrapped in ResponseEntity
                    return ResponseEntity.ok(convertToDTO(updatedComment));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());  // Handle comment not found scenario
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (commentService.isUserCommentOwner(id, userService.findByUsername(userDetails.getUsername()).getId())) {
            commentService.deleteComment(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/article/{articleId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByArticle(@PathVariable Long articleId) {
        List<Comment> comments = commentService.findCommentsByArticleId(articleId);
        List<CommentDTO> commentDTOs = comments.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(commentDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long id) {
        return commentService.getCommentById(id)
                .map(comment -> ResponseEntity.ok(convertToDTO(comment)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    public CommentDTO convertToDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setText(comment.getText());
        dto.setArticleId(comment.getArticle().getId());
        dto.setArticleTitle(comment.getArticle().getTitle());
        return dto;
    }

}
