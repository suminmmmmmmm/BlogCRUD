package com.example.blog.service;

import com.example.blog.domain.Article2;
import com.example.blog.domain.Comment;
import com.example.blog.domain.CommentRequest;
import com.example.blog.domain.CommentUpdate;
import com.example.blog.repository.BlogRepository2;
import com.example.blog.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor //??
public class CommentService {

    private final CommentRepository commentRepository;
    private final BlogRepository2 blogRepository2;

    //댓글 작성
    @Transactional
    public Comment addComment(Long articleId, CommentRequest request) {
        Article2 article = blogRepository2.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("Article not found: " + articleId));

        Comment comment = new Comment(
                request.getAuthor(),
                request.getContent(),
                article
        );
        return commentRepository.save(comment);
    }

    //댓글 전체조회
    public List<Comment> getComments(Long articleId) {
       return commentRepository.findAllByArticleId(articleId);
    }

    //댓글 수정
    @Transactional
    public Comment updateComment(Long commentId, CommentUpdate commentUpdate) {
       Comment comment = commentRepository.findById(commentId)
               .orElseThrow(() -> new IllegalArgumentException("Comment not found: " + commentId));
       comment.update(commentUpdate.getContent());
       return comment;
    }

    //댓글 삭제
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
