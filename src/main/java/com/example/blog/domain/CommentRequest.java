package com.example.blog.domain;

import com.example.blog.entity.Article;
import com.example.blog.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {
    private String author;
    private String content;

    public Comment toEntity(Article article){
        return Comment.builder()
                .content(content)
                .author(author)
                // 댓글은 게시물의 자식이기 때문에 필요
                .article(article)
                .build();
    }

}
