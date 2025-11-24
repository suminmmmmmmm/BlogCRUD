package com.example.blog.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

//서비스계층에서 요청을 받을 객체 생성
@NoArgsConstructor
@Getter
@AllArgsConstructor //??
public class AddArticleRequest2 {

    private String title;
    private String content;

    //DTO를 엔티티로 변환 --> 디비에 저장하기 위해!!!!!!!!!
    public Article2 toEntity() {
        return Article2.builder()
                .title(title)
                .content(content)
                .build();
    }
}
