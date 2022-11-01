package dev.com.application.usecases;

public class LoginResponseDto {
    String token;
    String refreshToken;

    public LoginResponseDto(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
