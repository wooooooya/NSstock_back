// PW 변경

package com.example.stocks.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPasswordUpdateRequestDto {
    private String currentPassword;
    private String newPassword;
}