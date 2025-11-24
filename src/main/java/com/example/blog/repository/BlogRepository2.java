package com.example.blog.repository;

import com.example.blog.domain.Article2;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository2 extends JpaRepository<Article2,Long> {
}
