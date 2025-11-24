package com.example.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.apache.bcel.classfile.Code;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class QuizControllerTest {


    @Autowired // 클래스 내부에서 필요한 객체를 찾아서 자동으로 넣어줌
    // 예를들면 UserRepository를 개발자가 new UserRepository() 하지 않아도 알아서 넣어줌
    protected MockMvc mockMvc; //MockMvc는 실제 서버 없이 테스트하게 해주는 가짜 웹서버

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper; //ObjectMapper은 json으로 변환

    @BeforeEach // 각 테스트 메서드 실행 전에 매번 실행
    public void mockMvcSetup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    } //초기화해 테스트 요청을 받을 준비를 한다.

    @DisplayName("quiz():GET /quiz?code=1이면 응답 코드는 201,응답 본문은 Created!를 리턴한다.")
    @Test
    public void getQuiz1() throws Exception {
        final String url = "/quiz";
        final ResultActions result = mockMvc.perform(get(url)
                .param("code","1"));

        result
                .andExpect(status().isCreated())
                .andExpect(content().string("Created!"));
    }

    @DisplayName("quiz():GET /quiz?code=2이면 응답 코드는 400,응답 본문은 Bad Request!를 리턴한다.")
    @Test
    public void getQuiz2() throws Exception {

        final String url = "/quiz";
        final  ResultActions result = mockMvc.perform(get(url)
                    .param("code","2"));

        result
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Bad Request!"));

    }

    @DisplayName("quiz():POST /quiz에 요청 본문이 {\"value\":1}이면 응답 코드는 403,응답 본문은 Forbidden!를 리턴한다.")
    @Test
    public void postQuiz1() throws Exception {

        final String url = "/quiz";
        final ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new QuizController.Code(1))));

        result
                .andExpect(status().isForbidden())
                .andExpect(content().string("Forbidden"));
    }

    @DisplayName("quiz():POST /quiz에 요청 본문이 {\"value\":13}이면 응답 코드는 200,응답 본문은 OK!를 리턴한다.")
    @Test
    public void postQuiz13() throws Exception {

        final String url = "/quiz";
        final ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new QuizController.Code(13))));

        result
                .andExpect(status().isOk())
                .andExpect(content().string("OK!!"));
    }
}