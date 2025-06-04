package in.abhi8290.helloworld.auth;

import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import in.abhi8290.helloworld.auth.dto.*;
import org.springframework.http.ResponseEntity;
import java.io.IOException;
import jakarta.servlet.http.Cookie;



@Controller
@RequestMapping("/auth")
public class AuthController {

  public static final Logger logger = LoggerFactory.getLogger(AuthController.class);
  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  // DONE
  @PostMapping("/register")
  public ResponseEntity<LoginResponseDto> register(@RequestBody RegisterUserDto request) throws Exception {
    LoginResponseDto loginResponse = authService.registerUser(request);
    return ResponseEntity.ok(loginResponse);
  }

  //DONE
  @PostMapping("/login") 
  public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) throws Exception {
    return ResponseEntity.ok(authService.authenticate(request.getUsername(), request.getPassword()));
  }

  // DONE
  @GetMapping("/oauth/{provider}")
  public void initiateOAuth(
          @PathVariable String provider,
          @RequestParam String callback_url,
          HttpServletResponse response
  ) throws IOException {

    String state = authService.createOAuthState(callback_url, provider);
    Cookie stateCookie = new Cookie("stateKey", state);
    
    stateCookie.setHttpOnly(false);
    stateCookie.setPath("/");
    stateCookie.setMaxAge(300);
    response.addCookie(stateCookie);
    response.sendRedirect("/oauth2/authorization/" + provider );
  }


  @PostMapping("/logout")
  public ResponseEntity<LoginResponseDto> logout(@RequestBody LoginRequestDto request) throws Exception {
    return ResponseEntity.ok(authService.logoutUser(request.getUsername()));
  }



//  @GetMapping("/me")


  @PostMapping("/token")
  public ResponseEntity<String> getRefreshedAccessToken(@RequestBody RefreshAccessTokenDto request) throws Exception {
    return ResponseEntity.ok(
        authService.getRefreshAccessToken(
            request.getRefreshToken()));
  }

}
