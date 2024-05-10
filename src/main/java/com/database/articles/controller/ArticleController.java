// ArticleController
package com.database.articles.controller;

import com.database.articles.DTO.ArticleDTO;
import com.database.articles.DTO.UserDTO;
import com.database.articles.model.Article;
import com.database.articles.model.User;
import com.database.articles.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.ArrayList; // Ensure this is imported for ArrayList usage
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * Get all articles
     * @param author
     * @param tag
     * @param keyword
     * @param month
     * @param year
     * @param page
     * @param size
     * @return
     */
    @GetMapping
    public ResponseEntity<?> getAllArticles(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        if (author == null && tag == null && keyword == null && month == null && year == null && page == null && size == null) {
            List<ArticleDTO> articles = articleService.getAllArticles();
            return ResponseEntity.ok(articles);
        } else {
            Page<ArticleDTO> articles = articleService.getArticles(
                    page != null ? page : 0,
                    size != null ? size : 100,
                    author, tag, keyword, month, year);
            return ResponseEntity.ok(articles);
        }}




    /**
     * Download articles as CSV
     * @param ids
     * @param author
     * @param tag
     * @param keyword
     * @param month
     * @param year
     * @return
     */

    @GetMapping("/csv")
    public ResponseEntity<String> downloadArticlesAsCSV(
            @RequestParam(required = false) List<Long> ids,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year) {

        List<ArticleDTO> articlesDTO;
        if (ids != null && !ids.isEmpty()) {
            articlesDTO = articleService.findArticlesByIds(ids);

        } else {
            // Apply filters if IDs are not provided, assume default pagination as all results in one go
            articlesDTO = articleService.getArticles(0, Integer.MAX_VALUE, author, tag, keyword, month, year).getContent();
        }

        String csvData = articleService.convertArticlesToCSV(articlesDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"articles.csv\"");
        headers.add("Content-Type", "text/csv");

        return ResponseEntity.ok()
                .headers(headers)
                .body(csvData);
    }

    /**
     * Get article by ID
     * @param id
     * @return
     */

    //Give javadoc for this method to explain what it does



    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
        return articleService.getArticleById(id)
                             .map(ResponseEntity::ok)
                             .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ArticleDTO> createArticle(@RequestBody ArticleDTO articleDTO, Authentication authentication) {
        String username = authentication.getName();
        ArticleDTO savedArticle = articleService.createArticle(articleDTO, username);
        return ResponseEntity.ok(savedArticle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleDTO> updateArticle(@PathVariable Long id, @RequestBody ArticleDTO articleDTO) {
        try {
            ArticleDTO updatedArticle = articleService.updateArticle(id, articleDTO);
            return ResponseEntity.ok(updatedArticle);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        try {
            articleService.deleteArticle(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
