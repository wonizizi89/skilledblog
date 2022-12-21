package com.example.myblog1.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor //세터 대신 DTO 생성자를 생성 하는 방법으로 @AllArgsConstructor 하였으나 변경가능성이 없기 때문에 굳이 사용할 필요없다고 하심 ,
//@Getter 와 @NoArgsConstructor 로 값을 set 할 수 있음
public class SignupRequest {

    private String username;
    private String password;
    private String email;
    private boolean admin = false;
    private String adminToken="";

}
