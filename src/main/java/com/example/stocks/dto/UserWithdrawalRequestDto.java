// 탈퇴

package com.example.stocks.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserWithdrawalRequestDto {
    private String loginId;
    private String password;
}
