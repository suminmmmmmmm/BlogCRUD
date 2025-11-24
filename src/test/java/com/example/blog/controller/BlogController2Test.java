package com.example.blog.controller;

import com.example.blog.domain.AddArticleRequest2;
import com.example.blog.domain.Article2;
import com.example.blog.domain.UpdateArticleRequest2;
import com.example.blog.repository.BlogRepository2;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BlogController2Test {

    @Autowired
    protected MockMvc mvc;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    private BlogRepository2 blogRepository2;

    @BeforeEach
    public void mockMvcsetUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(context).build();
        blogRepository2.deleteAll();
    }

    @DisplayName("addArticle: 블로그 글 추가에 성공한다.")
    @Test
    public void addArticle() throws Exception {

        final String url = "/api/article";
        final String title = "title";
        final String content = "content";

        final AddArticleRequest2 request = new AddArticleRequest2(title, content);
        final String requestBody = objectMapper.writeValueAsString(request);

        // POST 실행
        ResultActions result = mvc.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        );

        // HTTP 상태 코드 검증 (컨트롤러가 실제로 201을 반환해야 성공)
        result.andExpect(status().isCreated());

        // DB 저장 검사
        List<Article2> articles = blogRepository2.findAll();

        assertEquals(1, articles.size());
        assertEquals(title, articles.get(0).getTitle());
        assertEquals(content, articles.get(0).getContent());
    }

    @DisplayName("블로그 글 목록 조회에 성공한다.")
    @Test
    public void findAll() throws Exception {

        //given
        final String url = "/api/article";
        final String title = "title";
        final String content = "content";

        blogRepository2.save(Article2.builder()
                .title(title)
                        .content(content)
                .build());

        //when
        final ResultActions result = mvc.perform(
                get(url)
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(title))
                .andExpect(jsonPath("$[0].content").value(content));
    }

    @DisplayName("블로그 글 개별 조회에 성공한다.")
    @Test
    public void findById() throws Exception {

        //given
        final String url = "/api/article/{id}";
        final String title = "title";
        final String content = "content";

        Article2 savedarticle2 = blogRepository2.save(Article2.builder()
                .title(title)
                .content(content)

                .build());

        //when
        final ResultActions result = mvc.perform(
                get(url,savedarticle2.getId()));

        //then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.content").value(content));
    }




    @DisplayName("블로그 글 삭제에 성공한다.")
    @Test
    public void deleteById() throws Exception {

        //given
        final String url = "/api/article/{id}";
        final String title = "title";
        final String content = "content";

        Article2 savedarticle2 = blogRepository2.save(Article2.builder()
                .title(title)
                .content(content)

                .build());

        //when
        final ResultActions result = mvc.perform(
                delete(url,savedarticle2.getId()))
                .andExpect(status().isOk());

        //then
        List<Article2> articles = blogRepository2.findAll();

        assertThat(articles).isEmpty();
    }

    @DisplayName("블로그 글 수정에 성공한다.")
    @Test
    public void updateById() throws Exception {

        //given
        final String url = "/api/article/{id}";
        final String title = "title";
        final String content = "content";

        Article2 savedarticle2 = blogRepository2.save(Article2.builder()
                .title(title)
                .content(content)

                .build());

        final String newTitle = "new title";
        final String newContent = "new content";

        UpdateArticleRequest2 request2 = new UpdateArticleRequest2(newTitle, newContent);

        //when
        final ResultActions result = mvc.perform(
                put(url, savedarticle2.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request2))
        );

        //then

        result.andExpect(status().isOk());

        Article2 article2 = blogRepository2.findById(savedarticle2.getId()).get();

        assertThat(article2.getTitle()).isEqualTo(newTitle);
        assertThat(article2.getContent()).isEqualTo(newContent);
    }
}
