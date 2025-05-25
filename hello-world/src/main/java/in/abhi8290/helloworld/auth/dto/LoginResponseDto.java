package in.abhi8290.helloworld.auth.dto;

public class LoginResponseDto {
    String refreshToken;
    String accessToken;

    public LoginResponseDto(String refreshToken, String accessToken) {
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
    public String getAccessToken() {
        return accessToken;
    }
}


