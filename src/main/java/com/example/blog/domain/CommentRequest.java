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

    public Comment toEntity(Article article) {
       return Comment.builder()
               .article(article)
               .author(author)
               .content(content)
               .build();
    }

}
