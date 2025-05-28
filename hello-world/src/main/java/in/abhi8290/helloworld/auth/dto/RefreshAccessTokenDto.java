package in.abhi8290.helloworld.auth.dto;

public class RefreshAccessTokenDto {
  String refreshToken;

  public RefreshAccessTokenDto(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public String getRefreshToken() {
    return refreshToken;
  }
}
