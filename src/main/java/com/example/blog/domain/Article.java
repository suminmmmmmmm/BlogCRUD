package com.example.blog.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
// 파라미터가 없는 디폴트 생성자를 자동생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private long id;

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name = "content",nullable = false)
    private String content;


    // 빌더패턴을 사용하면 어느 필드에 어떤 값이 들어가는지 명시적으로 파악 가능
// 빌더를 사용하지 않았을 때    new Article("abc","def");
//  빌더를 사용했을 때
//    Article.builder()
//            .title("abc");
//            .content("def");
//            .build();

    @Builder
    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }


}
