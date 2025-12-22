package com.example.blog.service;

import com.example.blog.domain.ArticleRequest;
import com.example.blog.domain.UpdateRequest;
import com.example.blog.entity.Article;
import com.example.blog.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    // 글 작성
    public Article save(ArticleRequest request) {
        return articleRepository.save(request.toEntity());
    }
    // 작성한 글 전체 조회
    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    // 개별 조회
    public Article findOne(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 게시물은 존재하지 않습니다."));
    }

    // 삭제
    public void delete(Long id) {
        articleRepository.deleteById(id);
    }

    // 수정
    @Transactional
     public Article update(UpdateRequest article, Long id) {
        Article article1 = articleRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        article1.Update(article.getTitle(), article.getContent());
        return  article1;
     }
}
