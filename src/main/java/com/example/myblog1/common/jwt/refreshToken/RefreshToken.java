package com.example.myblog1.common.jwt.refreshToken;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Table(name = "T_REFRESH_TOKEN")
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="REFRESH_TOKEN_ID",nullable = false)
    private Long refreshTokenId;

    @Column(name = "REFRESH_TOKEN",nullable = false)
    private String refreshToken;
    @Column(name = "KEY_USERNAME", nullable = false)
    private String username;


    public void updateRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }



}
