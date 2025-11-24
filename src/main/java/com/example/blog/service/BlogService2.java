package com.example.blog.service;

import com.example.blog.domain.AddArticleRequest2;
import com.example.blog.domain.Article2;
import com.example.blog.domain.UpdateArticleRequest2;
import com.example.blog.repository.BlogRepository;
import com.example.blog.repository.BlogRepository2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService2 {

    private final BlogRepository2 blogRepository2;

    public Article2 save(AddArticleRequest2 request2) {
        return blogRepository2.save(request2.toEntity());
    }

    //전체 목록조회 --> 리스트형식으로 받아오기
    public List<Article2> findAll() {
        return blogRepository2.findAll();
    }

    //단건조회
    public Article2 findById(Long id) {
        return blogRepository2.findById(id)
                .orElseThrow(()->new IllegalArgumentException("not found"+id));
    }

    //삭제
    public void deleteById(Long id) {
        blogRepository2.deleteById(id);

    }

    @Transactional
    public Article2 update(long id, UpdateArticleRequest2 request2) {
        Article2 article2 = blogRepository2.findById(id)
                .orElseThrow(()->new IllegalArgumentException("not found"+id));
        article2.update(request2.getTitle(), request2.getContent());
        return article2;
    }
}
