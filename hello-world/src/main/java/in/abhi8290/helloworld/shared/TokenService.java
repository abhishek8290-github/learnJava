package in.abhi8290.helloworld.shared;

import in.abhi8290.helloworld.core.exception.common.InvalidTokenError;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import in.abhi8290.helloworld.user.model.User;
import java.security.Key;
import java.util.Date;

import in.abhi8290.helloworld.core.base.BaseService;
import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class TokenService extends BaseService<Token, String> {

  private final TokenRepository tokenRepository;
  // 32-byte secret for HS256; move to secure config/env for production
  private static final Key SECRET_KEY = Keys.hmacShaKeyFor("your-256-bit-secret-your-256-bit-secret".getBytes());

  // Access token expiry in milliseconds (15 minutes as per best practice)
  private static final long ACCESS_TOKEN_EXPIRATION_MS = 15 * 60 * 1000;

  // Spring will inject this automatically
  public TokenService(TokenRepository tokenRepository) {
    this.tokenRepository = tokenRepository;
  }

  @Override
  protected TokenRepository getRepository() {
    return tokenRepository;
  }

  /**
   * Generates a signed JWT to be used as a Bearer token
   *
   * @param userId The ID of the user (subject)
   * @return JWT access token (Bearer token)
   */
  public static String generateAccessToken(String userId) {
    Date now = new Date();
    Date expiry = new Date(now.getTime() + ACCESS_TOKEN_EXPIRATION_MS);

    return Jwts.builder()
        .setSubject(userId)
        .setIssuedAt(now)
        .setExpiration(expiry)
        .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
        .compact();
  }

  /**
   * Validates the token and extracts the user ID (subject)
   *
   * @param token The JWT string
   * @return user ID (from sub claim)
   * @throws JwtException if invalid or expired
   */
  public static String validateAccessToken(String token) throws Exception {

    try {
      return Jwts.parserBuilder()
          .setSigningKey(SECRET_KEY)
          .build()
          .parseClaimsJws(token)
          .getBody()
          .getSubject();
    } catch (RuntimeException e) {
      throw new InvalidTokenError("Token Provided is Not Valid");
    }

  }

  public static String getNewAccessTokenFromRefreshToken(String refreshToken) {
    return "This is a new Access Token Diksha Babe lets see how it goes ";
  }

  private String createRefreshToken() {
    return UUID.randomUUID().toString();
  }

  public String createRefreshToken(User user) {

    OffsetDateTime now = OffsetDateTime.now();

    OffsetDateTime tokenValidTill = now.plusDays(3);

    String _purpose = "generateAccessToken";
    String _refreshToken = createRefreshToken();

    Token _token = new Token(
        _refreshToken,
        _purpose,
        tokenValidTill,
        user);
    tokenRepository.save(_token);
    return _refreshToken;
  }

}
