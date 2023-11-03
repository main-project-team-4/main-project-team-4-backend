package com.example.demo.refreshToken;

import com.example.demo.security.UserRoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RefreshToken {

    private String username;

    public RefreshToken(String username) {
        this.username = username;
    }
}
