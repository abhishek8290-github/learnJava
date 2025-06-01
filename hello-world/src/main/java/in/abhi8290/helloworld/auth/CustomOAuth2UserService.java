package in.abhi8290.helloworld.auth;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.userinfo.*;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.user.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // Only enhance GitHub users
        if ("github".equalsIgnoreCase(registrationId)) {
            String token = userRequest.getAccessToken().getTokenValue();

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                    "https://api.github.com/user/emails",
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<>() {}
            );

            assert response.getBody() != null;
            String email = response.getBody().stream()
                    .filter(e -> Boolean.TRUE.equals(e.get("primary")) && Boolean.TRUE.equals(e.get("verified")))
                    .map(e -> (String) e.get("email"))
                    .findFirst()
                    .orElse("not-provided@example.com");

            // Add or replace email in attributes
            Map<String, Object> attributes = new HashMap<>(oauth2User.getAttributes());
            attributes.put("email", email);

            return new DefaultOAuth2User(
                    oauth2User.getAuthorities(),
                    attributes,
                    "id" // fallback key – ensure this exists or change it
            );
        }

        return oauth2User;
    }
}
