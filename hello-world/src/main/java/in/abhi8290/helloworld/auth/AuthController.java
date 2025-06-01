package in.abhi8290.helloworld.auth;

import in.abhi8290.helloworld.user.model.AuthProvider;
import in.abhi8290.helloworld.user.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import in.abhi8290.helloworld.auth.dto.*;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;


@Controller
@RequestMapping("/auth")
public class AuthController {

  public static final Logger logger = LoggerFactory.getLogger(AuthController.class);
  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/register")
  public ResponseEntity<LoginResponseDto> register(@RequestBody RegisterUserDto request) throws Exception {
    LoginResponseDto loginResponse = authService.registerUser(request);
    return ResponseEntity.ok(loginResponse);
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) throws Exception {
    return ResponseEntity.ok(authService.authenticate(request.getUsername(), request.getPassword()));
  }

  @GetMapping("/oauth2/success")
  public Object oauth2Success(
          @AuthenticationPrincipal OAuth2User principal,
          Model model,
          OAuth2AuthenticationToken authToken,
          HttpServletRequest request
  ) throws Exception {
    if (principal == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("OAuth login failed");
    }

    String registrationId = authToken.getAuthorizedClientRegistrationId(); // "google" or "github"

    logger.info("OAuth registration id {}", registrationId);
    logger.info("data {}", principal);


    Map<String, Object> attributes = principal.getAttributes();
    String name = (String) attributes.getOrDefault("name", attributes.get("login"));
    String email = (String) attributes.getOrDefault("email", "not provided");



    if ( email == null) {
      // Better Error !!
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("OAuth login failed");
    }

    AuthProvider authProvider = AuthProvider.LOCAL;

    if(Objects.equals(registrationId, "google")){
      authProvider = AuthProvider.GOOGLE;
    }
    if(Objects.equals(registrationId, "github")){
      authProvider = AuthProvider.GITHUB;
    }


    // Detect Accept header
    String accept = request.getHeader("Accept");
    boolean wantsJson = accept != null && accept.contains("application/json");

    User user = authService.findOrCreateOAuthUser(name, "", email, authProvider);

    if (wantsJson) {
      Map<String, Object> response = new HashMap<>();
      response.put("name", name);
      response.put("email", email);
      response.put("attributes", attributes);
      return ResponseEntity.ok(response);
    }

    // HTML view
    model.addAttribute("name", name);
    model.addAttribute("email", email);
    return "result"; // renders templates/result.html
  }


  @PostMapping("/logout")
  public ResponseEntity<LoginResponseDto> logout(@RequestBody LoginRequestDto request) throws Exception {
    return ResponseEntity.ok(authService.logoutUser(request.getUsername()));
  }

  @PostMapping("/token")
  public ResponseEntity<String> getRefreshedAccessToken(@RequestBody RefreshAccessTokenDto request) throws Exception {
    return ResponseEntity.ok(
        authService.getRefreshAccessToken(
            request.getRefreshToken()));
  }

}
