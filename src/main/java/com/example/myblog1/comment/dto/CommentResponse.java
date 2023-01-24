package com.example.myblog1.comment.dto;

import com.example.myblog1.comment.entity.Comment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
/*이 경우 초기값 세팅이 필요한 final 변수가 있을 경우 컴파일 에러가 발생함으로 주의한다.
@NoArgsConstructor(force=true) 를 사용하면 null, 0 등 기본 값으로 초기화 된다.*/
@NoArgsConstructor(force = true,access = AccessLevel.PROTECTED)
public class CommentResponse {
    private final Long id;
    private final String username;
    private final String comment;

    public CommentResponse(Comment comment) {
        this.id =comment.getId();
        this.username = comment.getUser().getUsername();
        this.comment = comment.getComment();
    }


}
