package com.example.blog.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// AddArticleRequest는 dto패키지를 컨트롤러에서 요청한 본문을 받을 객체
@Getter
// 파라미터가 없는 디폴트 생성자를 자동생성
@NoArgsConstructor
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 추가

public class UpdateArticleRequest {
    private String title;
    private String content;

}
