package com.example.blog.repository;

import com.example.blog.domain.Article2;
import com.example.blog.domain.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    boolean existsByUserIdAndArticle(String userId, Article2 article2);
    void deleteByUserIdAndArticle(String userId, Article2 article2);
    List<Scrap> findByUserId(String userId);
}
