package com.example.blog.controller;

import com.example.blog.domain.CommentRequest;
import com.example.blog.entity.Article;
import com.example.blog.entity.Comment;
import com.example.blog.repository.ArticleRepository;
import com.example.blog.repository.CommentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest {

    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleRepository articleRepository;

    @BeforeEach
    void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        commentRepository.deleteAll();
        articleRepository.deleteAll();
    }

    @DisplayName("댓글 생성 성공")
    @Test
    public void addComment() throws Exception {
        // 게시글 만들기
        Article article = articleRepository.save(
                Article.builder()
                        .title("title")
                        .content("content")
                        .build()
        );

        final String url = "/comments/{articleId}";
        final String author = "author";
        final String content = "content";
        //순서 바뀌면 안됨
        final CommentRequest request = new CommentRequest(author, content);
        final String requestBody = objectMapper.writeValueAsString(request);


        ResultActions result = mvc.perform(post(url,article.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody)
        );

        List<Comment> comments = commentRepository.findAll();

        assertEquals(1, comments.size());
        assertEquals(author, comments.get(0).getAuthor());
        assertEquals(content, comments.get(0).getContent());

    }

    @DisplayName("댓글 전체 조회")
    @Test
    public void findArticleComments() throws Exception {
        Article article = articleRepository.save(
                Article.builder()
                        .title("title")
                        .content("content")
                        .build()
        );

        final String url = "/comments/{articleId}";
        final String author = "author";
        final String content = "content";
        Comment saved = commentRepository.save(
                Comment.builder()
                        .article(article)
                        .author(author)
                        .content(content)
                        .build()
        );

        ResultActions result = mvc.perform(get(url,article.getId())
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].author").value(author))
                .andExpect(jsonPath("$[0].content").value(content));

    }
  
}