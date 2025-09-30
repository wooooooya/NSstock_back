// 회원가입

package com.example.stocks.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpRequestDto {
    private String loginId;
    private String password;
    private String email;
}