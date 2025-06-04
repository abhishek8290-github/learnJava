package in.abhi8290.helloworld.auth;

import in.abhi8290.helloworld.auth.dto.*;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

import in.abhi8290.helloworld.auth.model.OAuthStateData;
import in.abhi8290.helloworld.shared.util.hashUtil;
import in.abhi8290.helloworld.user.model.AuthProvider;
import in.abhi8290.helloworld.user.service.UserService;
import in.abhi8290.helloworld.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import in.abhi8290.helloworld.shared.TokenService;
import in.abhi8290.helloworld.auth.exception.UserNotFoundException;
import in.abhi8290.helloworld.auth.exception.IncorrectPasswordException;
import in.abhi8290.helloworld.user.model.AuthProvider;
import org.springframework.transaction.annotation.Transactional;



@Service
public class AuthService {

  public final UserService userService;
  public final TokenService tokenService;
  private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
  private static final String OAUTH_STATE_PREFIX = "oauth:state:";
  private static final String TEMP_TOKEN_PREFIX = "oauth:temp_token:";
  private static final int STATE_EXPIRY_MINUTES = 10; // Short expiry for security
  private static final int TEMP_TOKEN_EXPIRY_MINUTES = 5; // Very short expiry

  @Autowired
  private RedisTemplate<String, Object> redisTemplate;


  public AuthService(UserService userService , TokenService tokenService) {
    this.userService = userService;
    this.tokenService = tokenService;
    }

  public String getAccessToken(String userId) {
    return TokenService.generateAccessToken(userId);
  }

  public LoginResponseDto authenticate(String email, String password) throws Exception {

    Optional<User> currentUser = userService.findByEmail(email);

    if (currentUser.isEmpty()) {
      throw new UserNotFoundException("User not found with email: " + email);
    }
    boolean correctUser = hashUtil.verifyPassword(password, currentUser.get().getPassword());


    if (!correctUser) {
      throw new IncorrectPasswordException("Invalid Password Provided");
    }

      String refreshToken = tokenService.createRefreshToken(currentUser.get());

    return new LoginResponseDto(refreshToken, getAccessToken(currentUser.get().getId()));

  }

  public LoginResponseDto registerUser(RegisterUserDto userToCreate) throws Exception {

    User user = userService.createUser(new User(
        userToCreate.getFirstName(),
        userToCreate.getLastName(),
        userToCreate.getEmail(),
        userToCreate.getPassword(), AuthProvider.LOCAL
    ));

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
      return TokenService.getNewAccessTokenFromRefreshToken(refreshToken);
  }


  public User findOrCreateOAuthUser(String firstName, String lastName, String email, AuthProvider provider) throws Exception {
    Optional<User> existingUser = userService.findByEmail(email);

    if (existingUser.isPresent()) {
      return existingUser.get();
    }

    // Create a random password since social login users won't use it
    String randomPassword = UUID.randomUUID().toString();



    User newUser = new User();
    newUser.setFirstName(firstName != null ? firstName : "OAuth");
    newUser.setLastName(lastName != null ? lastName : "User");
    newUser.setEmail(email);
    newUser.setPassword(randomPassword);
    newUser.setProvider(provider);
    newUser.setEmailVerified(true);
    return userService.createUser(newUser);
  }


  public String createOAuthState(String callbackUrl, String provider) {
    String state = UUID.randomUUID().toString();
    String redisKey = OAUTH_STATE_PREFIX + state;

    OAuthStateData stateData = new OAuthStateData(callbackUrl, provider, System.currentTimeMillis());

    try {
      redisTemplate.opsForValue().set(
              redisKey,
              stateData,
              Duration.ofMinutes(STATE_EXPIRY_MINUTES)
      );

      logger.info("Created OAuth state: {} for callback: {}", state, callbackUrl);
      return state;

    } catch (Exception e) {
      logger.error("Failed to create OAuth state", e);
      throw new RuntimeException("Failed to create OAuth state", e);
    }
  }
  public OAuthStateData getAndRemoveOAuthState(String state) {
    if (state == null || state.trim().isEmpty()) {
        return null;
    }
    
    String redisKey = OAUTH_STATE_PREFIX + state;
    
    try {
        OAuthStateData stateData = (OAuthStateData) redisTemplate.opsForValue().get(redisKey);
        
        if (stateData != null) {
            // Remove it immediately (one-time use for security)
            redisTemplate.delete(redisKey);
            logger.info("Retrieved and removed OAuth state: {}", state);
            return stateData;
        }
        
        logger.warn("OAuth state not found or expired: {}", state);
        return null;
        
    } catch (Exception e) {
        logger.error("Failed to retrieve OAuth state", e);
        return null;
    }
}


}
