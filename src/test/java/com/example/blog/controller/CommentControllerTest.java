package com.example.blog.controller;

import com.example.blog.domain.Article2;
import com.example.blog.domain.Comment;
import com.example.blog.domain.CommentRequest;
import com.example.blog.domain.CommentUpdate;
import com.example.blog.repository.BlogRepository2;
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

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest {

    @Autowired
    protected MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BlogRepository2 blogRepository2;

    @BeforeEach
    public void mockMvcsetUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        commentRepository.deleteAll();
        blogRepository2.deleteAll();
    }

    @DisplayName("댓글 추가에 성공한다.")
    @Test
    public void addComment() throws Exception {

        // 테스트용 게시글 먼저 저장해야 외래키 오류 없음
        Article2 article = blogRepository2.save(
                Article2.builder()
                        .title("title")
                        .content("content")
                        .build()
        );

        final String url = "/api/article/{articleId}/comments";
        final String content = "content";
        final String author = "author";

        final CommentRequest request = new CommentRequest(content, author);
        final String requestBody = objectMapper.writeValueAsString(request);

        // 🟩 게시글 ID(articleId)를 PathVariable로 전달
        ResultActions result = mvc.perform(
                post(url, article.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        );

        result.andExpect(status().isCreated());

        List<Comment> comments = commentRepository.findAll();

        assertEquals(1, comments.size());
        assertEquals(author, comments.get(0).getAuthor());
        assertEquals(content, comments.get(0).getContent());
    }

    @DisplayName("댓글 목록 조회에 성공")
    @Test
    public void findAll() throws Exception {

        // given
        final String url = "/api/article/{articleId}/comments";

        final String content = "content";
        final String author = "author";

        // (1) 게시글 먼저 저장
        Article2 article = blogRepository2.save(
                Article2.builder()
                        .title("title")
                        .content("content")
                        .build()
        );

        // (2) 댓글 저장 시 article 반드시 설정
        commentRepository.save(
                Comment.builder()
                        .author(author)
                        .content(content)
                        .article(article)
                        .build()
        );

        // when
        ResultActions result = mvc.perform(
                get(url, article.getId())  // 여기 articleId 필요!
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].author").value(author))
                .andExpect(jsonPath("$[0].content").value(content));
    }

    @DisplayName("댓글 삭제 성공")
    @Test
    public void deleteById() throws Exception {

        final String url = "/api/comments/{articleId}";
        final String oldContent = "content";
        final String oldAuthor = "author";
        // 게시글 먼저 저장
        Article2 article = blogRepository2.save(
                Article2.builder()
                        .title("title")
                        .content("content")
                        .build()
        );

        // 댓글 저장
        Comment savedComment = commentRepository.save(
                Comment.builder()
                        .author(oldAuthor)
                        .content(oldContent)
                        .article(article)
                        .build()
        );

        final ResultActions result = mvc.perform(
                delete(url, savedComment.getId()))
                .andExpect(status().isOk());

        List<Comment> comments = commentRepository.findAll();

        assertThat(comments).isEmpty();

    }

    @DisplayName("댓글 수정에 성공")
    @Test
    public void updateById() throws Exception {

        final String url = "/api/comments/{articleId}";
        final String oldContent = "content";
        final String oldAuthor = "author";

        // (1) 게시글 저장
        Article2 article = blogRepository2.save(
                Article2.builder()
                        .title("title")
                        .content("article-content")
                        .build()
        );

        // (2) 댓글 저장
        Comment savedComment = commentRepository.save(
                Comment.builder()
                        .author(oldAuthor)
                        .content(oldContent)
                        .article(article)
                        .build()
        );

        // (3) 수정 요청 DTO
        final String newContent = "newContent";
        CommentUpdate update = new CommentUpdate(newContent);

        // (4) PUT 요청 (❗ savedComment.getId() 사용)
        ResultActions result = mvc.perform(
                put(url, savedComment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update))
        );

        result.andExpect(status().isOk());

        // (5) DB 검증
        Comment updated = commentRepository.findById(savedComment.getId()).get();
        assertThat(updated.getContent()).isEqualTo(newContent);
    }

}
