package com.example.blog.service;

import com.example.blog.domain.CommentRequest;
import com.example.blog.entity.Article;
import com.example.blog.entity.Comment;
import com.example.blog.repository.ArticleRepository;
import com.example.blog.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    // 댓글 생성
    public Comment saveComment(Long id, CommentRequest commentRequest) {
        //(1) 댓글 달 게시물이 존재하는가
        Article article = articleRepository.findById(id)
                // (1-2) 게시물이 존재하지 않는다면 오류
                .orElseThrow(()-> new IllegalArgumentException("게시물을 찾을 수 없습니다."));
        //(1-3) 존재한다면 댓글 생성
        return commentRepository.save(commentRequest.toEntity(article));
    }

    // 댓글 조회
    public List<Comment> getComments(Long articleId) {
        return commentRepository.findByArticleId(articleId);
    }



}
