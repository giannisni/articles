package com.database.articles;

import com.database.articles.model.Comment;
import com.database.articles.model.User;
import com.database.articles.repository.CommentRepository;
import com.database.articles.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveComment() {
        Comment comment = new Comment();
        comment.setText("Test Comment");
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        Comment savedComment = commentService.saveComment(comment);

        assertThat(savedComment.getText()).isEqualTo("Test Comment");
        verify(commentRepository).save(comment);
    }

    //create a test for createArticle method

    @Test
    void testGetCommentById() {
        Optional<Comment> comment = Optional.of(new Comment());
        when(commentRepository.findById(1L)).thenReturn(comment);

        Optional<Comment> found = commentService.getCommentById(1L);

        assertThat(found.isPresent()).isTrue();
        verify(commentRepository).findById(1L);
    }

    @Test
    void testDeleteComment() {
        doNothing().when(commentRepository).deleteById(1L);
        commentService.deleteComment(1L);
        verify(commentRepository).deleteById(1L);
    }

    @Test
    void testIsUserCommentOwner() {
        User user = new User();
        user.setId(1L);

        Comment comment = new Comment();
        comment.setUser(user);

        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        boolean isOwner = commentService.isUserCommentOwner(1L, 1L);

        assertThat(isOwner).isTrue();
        verify(commentRepository).findById(1L);
    }
}
