package com.example.blog.service;

import com.example.blog.domain.Article2;
import com.example.blog.domain.Like;
import com.example.blog.repository.ArticleLikeRepository;
import com.example.blog.repository.BlogRepository2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final ArticleLikeRepository likeRepository;
    private final BlogRepository2 articleRepository;

    @Transactional
    public int toggleLike(Long articleId, String userId) {

        Article2 article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        boolean exists = likeRepository.existsByUserIdAndArticle(userId, article);

        if (exists) {
            likeRepository.deleteByUserIdAndArticle(userId, article);
        } else {
            likeRepository.save(
                    Like.builder()
                            .userId(userId)
                            .article(article)
                            .build()
            );
        }

        return likeRepository.countByArticle(article);
    }
}
