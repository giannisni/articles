package com.database.articles.util;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import com.database.articles.model.Article;


public class ArticleSpecifications {

    public static Specification<Article> hasAuthor(String author) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("authors"), author);
    }

    public static Specification<Article> hasTag(String tag) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("tags"), tag);
    }

    public static Specification<Article> hasKeyword(String keyword) {
        return (root, query, criteriaBuilder) -> {
            Predicate titlePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + keyword.toLowerCase() + "%");
            Predicate abstractPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("abstractText")), "%" + keyword.toLowerCase() + "%");
            return criteriaBuilder.or(titlePredicate, abstractPredicate);
        };
    }


    public static Specification<Article> isPublishedInMonthYear(int month, int year) {
        return (root, query, criteriaBuilder) -> {
            // Using EXTRACT function for PostgreSQL
            Expression<Integer> monthExpression = criteriaBuilder.function("EXTRACT", Integer.class,
                    criteriaBuilder.literal("MONTH"), root.get("publicationDate"));
            Expression<Integer> yearExpression = criteriaBuilder.function("EXTRACT", Integer.class,
                    criteriaBuilder.literal("YEAR"), root.get("publicationDate"));

            return criteriaBuilder.and(
                    criteriaBuilder.equal(monthExpression, month),
                    criteriaBuilder.equal(yearExpression, year)
            );
        };
    }




}
