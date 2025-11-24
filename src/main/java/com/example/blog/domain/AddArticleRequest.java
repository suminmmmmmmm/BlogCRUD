package com.example.blog.domain;

import jakarta.persistence.*;
import lombok.*;
// AddArticleRequest는 dto패키지를 컨트롤러에서 요청한 본문을 받을 객체
@Getter
// 파라미터가 없는 디폴트 생성자를 자동생성
@NoArgsConstructor
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 추가

public class AddArticleRequest {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id",nullable = false)
//    private long id;

//    @Column(name = "title",nullable = false)
    private String title;

//    @Column(name = "content",nullable = false)
    private String content;


    // 빌더패턴을 사용하면 어느 필드에 어떤 값이 들어가는지 명시적으로 파악 가능
// 빌더를 사용하지 않았을 때    new Article("abc","def");
//  빌더를 사용했을 때
//    Article.builder()
//            .title("abc");
//            .content("def");
//            .build();


    // toEntity는 빌더 패턴을 사용해 dto를 엔티티로 만들어줌
public Article toEntity() {
    return Article.builder()
            .title(title)
            .content(content)
            .build();
    }
}
