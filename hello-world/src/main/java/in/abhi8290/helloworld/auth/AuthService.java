package in.abhi8290.helloworld.auth;

import in.abhi8290.helloworld.user.*;
import in.abhi8290.helloworld.auth.dto.*;
import java.util.Optional;
import in.abhi8290.helloworld.shared.util.hashUtil;
import org.springframework.stereotype.Service;
import in.abhi8290.helloworld.shared.TokenService;
import in.abhi8290.helloworld.auth.exception.UserNotFoundException;
import in.abhi8290.helloworld.auth.exception.IncorrectPasswordException;




@Service
public class AuthService {

  public final UserService userService;
  public final TokenService tokenService;


  public AuthService(UserService userService , TokenService tokenService) {
    this.userService = userService;
    this.tokenService = tokenService;
    }

  public String getAccessToken(String userId) {
    return TokenService.generateAccessToken(userId);
  }

  public LoginResponseDto authenticate(String email, String password) throws Exception {

    Optional<User> currentUser = userService.findByEmail(email);
    ;

    if (currentUser.isEmpty()) {
      throw new UserNotFoundException("User not found with email: " + email);
    }
    boolean correctUser = hashUtil.verifyPassword(password, currentUser.get().getPassword());


    if (!correctUser)
      throw new IncorrectPasswordException("Invalid Password Provided");

      String refreshToken = tokenService.createRefreshToken(currentUser.get());

    return new LoginResponseDto(refreshToken, getAccessToken(currentUser.get().getId()));

  }

  public LoginResponseDto registerUser(RegisterUserDto userToCreate) throws Exception {

    User user = userService.createUser(new User(
        userToCreate.getFirstName(),
        userToCreate.getLastName(),
        userToCreate.getEmail(),
        userToCreate.getPassword()));

    return new LoginResponseDto("Refresh token ", getAccessToken(user.getId()));
  }

  public LoginResponseDto logoutUser(String userId) throws Exception {
    Optional<User> currentUser = userService.findById(userId);
    
    if (currentUser.isEmpty()) {
      throw new UserNotFoundException("User not found with email: " + userId);
    }
    String refreshToken = tokenService.createRefreshToken(currentUser.get());
    return new LoginResponseDto(refreshToken, getAccessToken(currentUser.get().getId()));

  }

  public String getRefreshAccessToken(String refreshToken) throws Exception {
    String accessToken = TokenService.getNewAccessTokenFromRefreshToken(refreshToken);
    return accessToken;
  }

}
