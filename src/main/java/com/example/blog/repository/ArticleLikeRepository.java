package com.example.blog.repository;


import com.example.blog.domain.Article;
import com.example.blog.domain.Article2;
import com.example.blog.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleLikeRepository extends JpaRepository<Like, Long> {

    boolean existsByUserIdAndArticle(String userId, Article2 article);

    void deleteByUserIdAndArticle(String userId, Article2 article);

    int countByArticle(Article2 article);
}
