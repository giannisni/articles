// ArticleService
package com.database.articles.service;

import com.database.articles.DTO.ArticleDTO;
import com.database.articles.DTO.UserDTO;
import com.database.articles.config.FeatureConfiguration;
import com.database.articles.config.FeatureProperties;
import com.database.articles.model.Article;
import com.database.articles.model.User;
import com.database.articles.repository.ArticleRepository;
import com.database.articles.repository.UserRepository;
import com.database.articles.util.ArticleSpecifications;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(ArticleService.class);

    @Autowired
    private FeatureProperties featureProperties;

    private final FeatureConfiguration featureConfiguration;

    @Autowired
    public ArticleService(FeatureConfiguration featureConfiguration, ArticleRepository articleRepository, UserRepository userRepository) {
        this.featureConfiguration = featureConfiguration;
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    // Setter methods for testing
    public void setArticleRepository(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    @PostConstruct
//    public void init() {
//        log.info("Is full-text search enabled? {}", featureProperties.isFulltextSearchEnabled());
//    }

    public List<ArticleDTO> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream().map(this::convertToArticleDTO).collect(Collectors.toList());
    }


    private ArticleDTO convertToArticleDTO(Article article) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(article.getId());
        dto.setTitle(article.getTitle());
        dto.setAbstractText(article.getAbstractText());
        dto.setPublicationDate(article.getPublicationDate());
        dto.setAuthors(new HashSet<>(article.getAuthors()));
        dto.setTags(new HashSet<>(article.getTags()));
        dto.setUser(convertToUserDTO(article.getUser()));
        return dto;
    }

    private UserDTO convertToUserDTO(User user) {
        if (user == null) {
            return null; // Or create a default UserDTO as needed
        }
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        return dto;
    }


    public List<ArticleDTO> findArticleDTOsByIds(List<Long> ids) {
        List<Article> articles = articleRepository.findAllById(ids);
        return articles.stream()
                .map(this::convertToArticleDTO)
                .collect(Collectors.toList());
    }



    public Optional<Article> getArticleById(Long id) {
        return articleRepository.findById(id);
    }

    @Transactional
    public Article saveArticle(Article article) {
        return articleRepository.save(article);
    }

    @Transactional
    public void deleteAllArticles() {
        articleRepository.deleteAll();
    }




    @Transactional
    public ArticleDTO createArticle(ArticleDTO articleDTO, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Article article = new Article();
        article.setTitle(articleDTO.getTitle());
        article.setAbstractText(articleDTO.getAbstractText());
        article.setPublicationDate(articleDTO.getPublicationDate());

        // Safely handle potential null values for collections
        article.setAuthors(articleDTO.getAuthors() != null ? new HashSet<>(articleDTO.getAuthors()) : new HashSet<>());
        article.setTags(articleDTO.getTags() != null ? new HashSet<>(articleDTO.getTags()) : new HashSet<>());
        article.setUser(user);

        Article savedArticle = articleRepository.save(article);
        return convertToArticleDTO(savedArticle);
    }



    @Transactional
    public ArticleDTO updateArticle(Long id, ArticleDTO articleDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = ((UserDetails) authentication.getPrincipal()).getUsername();

        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found"));

        if (!article.getUser().getUsername().equals(currentUsername)) {
            throw new IllegalStateException("Unauthorized to update this article");
        }

        // Map the DTO to the existing entity
        article.setTitle(articleDTO.getTitle());
        article.setAbstractText(articleDTO.getAbstractText());
        article.setAuthors(new HashSet<>(articleDTO.getAuthors()));
        article.setTags(new HashSet<>(articleDTO.getTags()));

        Article savedArticle = articleRepository.save(article);
        return convertToArticleDTO(savedArticle);
    }

    @Transactional
    public void deleteArticle(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = ((UserDetails) authentication.getPrincipal()).getUsername();

        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found"));

        if (!article.getUser().getUsername().equals(currentUsername)) {
            throw new IllegalStateException("Unauthorized to delete this article");
        }

        articleRepository.delete(article);
    }


    public List<ArticleDTO> findArticlesByIds(List<Long> ids) {
        List<Article> articles = articleRepository.findAllById(ids);
        return articles.stream()
                .map(this::convertToArticleDTO) // Assuming convertToArticleDTO is already implemented correctly
                .collect(Collectors.toList());
    }
    public String convertArticlesToCSV(List<ArticleDTO> articlesDTO) {
        StringBuilder csvOutput = new StringBuilder("ID,Title,Abstract,PublicationDate,Authors,Tags,Username\n");
        for (ArticleDTO article : articlesDTO) {
            csvOutput.append(article.getId()).append(",");
            csvOutput.append(article.getTitle()).append(",");
            csvOutput.append(article.getAbstractText()).append(",");
            csvOutput.append(article.getPublicationDate()).append(",");
            csvOutput.append(article.getAuthors().stream().collect(Collectors.joining(";"))).append(",");
            csvOutput.append(article.getTags().stream().collect(Collectors.joining(";"))).append(",");
            csvOutput.append(article.getUser().getUsername()).append("\n");
        }
        return csvOutput.toString();
    }



    public Page<ArticleDTO> getArticles(int page, int size, String author, String tag, String keyword, Integer month, Integer year) {
        if (keyword != null && featureConfiguration.isFulltextSearchEnabled()) {
            return articleRepository.findByKeywordUsingFullText(keyword, PageRequest.of(page, size))
                    .map(this::convertToArticleDTO);
        } else {
            Specification<Article> spec = Specification.where(null);
            if (author != null) {
                spec = spec.and(ArticleSpecifications.hasAuthor(author));
            }
            if (tag != null) {
                spec = spec.and(ArticleSpecifications.hasTag(tag));
            }
            if (keyword != null) {
                spec = spec.and(ArticleSpecifications.hasKeyword(keyword));
            }
            if (month != null && year != null) {
                spec = spec.and(ArticleSpecifications.isPublishedInMonthYear(month, year));
            }

            Page<Article> articlePage = articleRepository.findAll(spec, PageRequest.of(page, size));
            return articlePage.map(this::convertToArticleDTO);
        }

}}
