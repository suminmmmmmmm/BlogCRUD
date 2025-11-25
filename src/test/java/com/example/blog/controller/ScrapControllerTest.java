package com.example.blog.controller;

import com.example.blog.domain.Article2;
import com.example.blog.repository.BlogRepository2;
import com.example.blog.repository.ScrapRepository;
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
class ScrapControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    BlogRepository2 blogRepository;

    @Autowired
    ScrapRepository scrapRepository;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        blogRepository.deleteAll();
        scrapRepository.deleteAll();
    }

    @DisplayName("게시물 스크랩 true/false 토글 테스트")
    @Test
    void getScrap() throws Exception {

        // 1) 게시글 저장
        Article2 article2 = blogRepository.save(
                Article2.builder()
                        .title("title2")
                        .content("content2")
                        .build()
        );

        final String userId = "user1";
        final String url = "/api/article/{articleId}/scrap?userId=" + userId;

        // ✔ 첫 번째 요청: 스크랩 추가 → true 반환
        ResultActions result1 = mvc.perform(
                post(url, article2.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result1.andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));

        assertTrue(scrapRepository.existsByUserIdAndArticle(userId, article2));


        // ✔ 두 번째 요청: 스크랩 취소 → false 반환
        ResultActions result2 = mvc.perform(
                post(url, article2.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result2.andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));

        assertFalse(scrapRepository.existsByUserIdAndArticle(userId, article2));
    }
}
