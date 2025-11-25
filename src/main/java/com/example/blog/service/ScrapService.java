package com.example.blog.service;

import com.example.blog.domain.Article2;
import com.example.blog.domain.Scrap;
import com.example.blog.repository.BlogRepository2;
import com.example.blog.repository.ScrapRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScrapService {

    private final ScrapRepository scrapRepository;
    private final BlogRepository2 articleRepository;

    @Transactional
    public boolean toggleScrap(Long articleId, String userId) {

        Article2 article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        boolean exists = scrapRepository.existsByUserIdAndArticle(userId, article);

        if (exists) {
            scrapRepository.deleteByUserIdAndArticle(userId, article);
            return false; // 스크랩 취소
        } else {
            scrapRepository.save(new Scrap(userId, article));
            return true; // 스크랩 완료
        }
    }

    public List<Scrap> getUserScraps(String userId) {
        return scrapRepository.findByUserId(userId);
    }
}
