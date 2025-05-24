package in.abhi8290.helloworld.auth.dto;

public class LoginRequestDto {
    String username;
    String password;

    public LoginRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return username;
    }
}


