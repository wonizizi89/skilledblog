package com.example.myblog1.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor //세터 대신 DTO 생성자를 생성 하는 방법으로 @AllArgsConstructor 하였으나 변경가능성이 없기 때문에 굳이 사용할 필요없다고 하심
//@Getter 와 @NoArgsConstructor 로 값을 set 할 수 있음
public class SignupRequest {

    @NotBlank
    @Size(min=4,max=10)
    @Pattern(regexp = "^[a-z0-9]*$", message =" a-z , 0-9 만 입력하세요.")
    private String username;

    @NotBlank
    @Size(min=8,max=15)
    @Pattern(regexp = "^[A-Za-z0-9]*$", message =" A-Z,a-z , 0-9 만 입력하세요.")
    private String password;

    @Email
    @NotBlank
    private String email;


    private boolean admin = false;
    private String adminToken="";

}
