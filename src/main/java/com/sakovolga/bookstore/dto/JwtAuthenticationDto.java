package com.sakovolga.bookstore.dto;


public class JwtAuthenticationDto {
    private String token;
    private String refreshToken;

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "JwtAuthenticationDto{" +
                "token='" + token + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
