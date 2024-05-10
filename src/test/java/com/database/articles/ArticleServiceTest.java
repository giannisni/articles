package com.database.articles;

import com.database.articles.DTO.ArticleDTO;
import com.database.articles.config.FeatureConfiguration;
import com.database.articles.model.Article;
import com.database.articles.model.User;
import com.database.articles.repository.ArticleRepository;
import com.database.articles.repository.UserRepository;
import com.database.articles.service.ArticleService;
import com.database.articles.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FeatureConfiguration featureConfiguration;

    private ArticleService articleService;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        // Assuming ArticleService can accept mocked repositories via constructor
//        articleService = new ArticleService(featureConfiguration, articleRepository, userRepository);
//    }
    @BeforeEach
    void setUp() {
        // Initialize mocks created with the @Mock annotation
        MockitoAnnotations.openMocks(this);
        // Create the service with mocked dependencies
        articleService = new ArticleService(featureConfiguration, articleRepository, userRepository);
    }

    //create a test for getAllArticles method
    @Test
    void testGetAllArticles() {
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("user1");

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("user2");

        Article article1 = new Article();
        article1.setId(1L);
        article1.setUser(user1);

        Article article2 = new Article();
        article2.setId(2L);
        article2.setUser(user2);

        when(articleRepository.findAll()).thenReturn(Arrays.asList(article1, article2));

        List<ArticleDTO> results = articleService.getAllArticles();
        assertThat(results).hasSize(2);
        verify(articleRepository).findAll();
    }






    @Test
    void testCreateArticle() {
        // Prepare the user object with necessary fields
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("username");

        // Setup the ArticleDTO
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setTitle("New Article");
        articleDTO.setAuthors(new HashSet<>());  // Initialize to avoid NullPointerException
        articleDTO.setTags(new HashSet<>());     // Initialize to avoid NullPointerException

        // Mock behaviors
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(mockUser));
        when(articleRepository.save(any(Article.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Call the method under test
        ArticleDTO result = articleService.createArticle(articleDTO, "username");

        // Assertions to confirm behaviors
        assertNotNull(result);
        assertEquals("New Article", result.getTitle());
        verify(articleRepository).save(any(Article.class));
    }





//    @Test
//    void testUpdateArticleUnauthorized() {
//        User user = new User();
//        user.setId(1L);
//        user.setUsername("user2");
//
//        Article article = new Article();
//        article.setId(1L);
//        article.setUser(user);
//
//        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));
//
//        assertThrows(IllegalStateException.class, () -> {
//            articleService.updateArticle(1L, new ArticleDTO());
//        });
//    }

    @Test
    void testDeleteArticle() {
        // Mock user and article setup
        User user = new User();
        user.setId(1L);
        user.setUsername("user1");

        Article article = new Article();
        article.setId(1L);
        article.setUser(user);

        // Setup repository mocks
        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));

        // Mock Authentication and Security Context
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("user1");
        when(authentication.getPrincipal()).thenReturn(userDetails);

        // Execute the method under test
        assertDoesNotThrow(() -> articleService.deleteArticle(1L));

        // Verify interactions
        verify(articleRepository).delete(article);
    }


    @Test
    void testGetArticlesWithFilters() {
        User user = new User(); // Ensure there's a user object
        user.setId(1L);
        user.setUsername("testUser");

        Article article1 = new Article();
        article1.setId(1L);
        article1.setUser(user); // Set user
        Article article2 = new Article();
        article2.setId(2L);
        article2.setUser(user); // Set user
        Page<Article> page = new PageImpl<>(Arrays.asList(article1, article2));

        when(articleRepository.findAll(ArgumentMatchers.<Specification<Article>>any(), ArgumentMatchers.<PageRequest>any()))
                .thenReturn(page);

        Page<ArticleDTO> results = articleService.getArticles(0, 10, null, null, "keyword", null, null);
        assertThat(results.getContent()).hasSize(2);
        verify(articleRepository).findAll(ArgumentMatchers.<Specification<Article>>any(), ArgumentMatchers.<PageRequest>any());
    }

}
