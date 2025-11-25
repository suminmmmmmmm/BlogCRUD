package com.example.blog.controller;

import com.example.blog.domain.Article;
import com.example.blog.domain.Article2;
import com.example.blog.repository.ArticleLikeRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)

class LikeControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    BlogRepository2 blogRepository;
    @Autowired
    ArticleLikeRepository articleLikeRepository;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        blogRepository.deleteAll();
        articleLikeRepository.deleteAll();
    }

    @DisplayName("게시글 좋아요 토글")
    @Test
    void toggleLike() throws Exception {

        Article2 article = blogRepository.save(
                Article2.builder()
                        .title("title")
                        .content("content")
                        .build()
        );

        final String userId = "user1";
        final String url = "/api/article/{id}/like?userId=" + userId;

        ResultActions result1 = mvc.perform(
                post(url,article.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result1.andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1));
        assertEquals(1,articleLikeRepository.countByArticle(article));

        ResultActions result2 = mvc.perform(
                post(url,article.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        );


        result2.andExpect(status().isOk())
                .andExpect(jsonPath("$").value(0));

        assertEquals(0,articleLikeRepository.countByArticle(article));
    }

}