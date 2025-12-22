package com.example.blog.controller;

import com.example.blog.domain.ArticleRequest;
import com.example.blog.domain.UpdateRequest;
import com.example.blog.entity.Article;
import com.example.blog.repository.ArticleRepository;
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
class ArticleControllerTest {

    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        articleRepository.deleteAll();
    }

    @DisplayName("글 작성에 성공하였습니다.")
    @Test
    public void getArticle() throws Exception {
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";
        final ArticleRequest userRequest = new ArticleRequest(title, content);

        final String request = objectMapper.writeValueAsString(userRequest);

        final ResultActions result = mvc.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
        );

        result.andExpect(status().isCreated());

        List<Article> articles = articleRepository.findAll();

        assertEquals(1, articles.size());
        assertEquals(title, articles.get(0).getTitle());
        assertEquals(content, articles.get(0).getContent());
    }

    @DisplayName("게시물 전체 조회")
    @Test
    public void postArticle() throws Exception {
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";

        articleRepository.save(
                Article.builder()
                        .title(title)
                        .content(content)
                        .build()
        );

        final ResultActions result = mvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON)
        );

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(title))
                .andExpect(jsonPath("$[0].content").value(content));
    }

    @DisplayName("개별조회")
    @Test
    public void idArticle() throws Exception {
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";


      Article saved =  articleRepository.save(
                Article.builder()
                        .title(title)
                        .content(content)
                        .build()
        );

      final ResultActions result = mvc.perform(get(url, saved.getId()));

      //List<Article> articles = articleRepository.findAll();

      result
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.title").value(title))
              .andExpect(jsonPath("$.content").value(content));


    }

    @DisplayName("삭제")
    @Test
    public void deleteArticle() throws Exception {
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        Article saved = articleRepository.save(
                Article.builder()
                        .title(title)
                        .content(content)
                        .build()
        );

        //글 삭제
        final ResultActions result = mvc.perform(
                        delete(url,saved.getId()))
                .andExpect(status().isOk());

        List<Article> articles = articleRepository.findAll();

        assertThat(articles).isEmpty();
    }

    @DisplayName("수정완료")
    @Test
    public void updateArticle() throws Exception {
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";
        Article saved = articleRepository.save(
                Article.builder()
                        .title(title)
                        .content(content)
                        .build()
        );

        final String newT = "newT";
        final String newC = "newC";

        UpdateRequest request = new UpdateRequest(newT, newC);

        ResultActions result = mvc.perform(put(url,saved.getId())

        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        );

        result.andExpect(status().isOk());
        Article article = articleRepository.findById(saved.getId()).get();

        assertThat(article.getTitle()).isEqualTo(newT);
        assertThat(article.getContent()).isEqualTo(newC);




    }



}