package com.sakovolga.bookstore.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtAuthenticationDto {
    private String token;
    private String refreshToken;

    @Override
    public String toString() {
        return "JwtAuthenticationDto{" +
                "token='" + token + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
