// 로그인 요청

package com.example.stocks.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequestDto {
    private String loginId;
    private String password;
}