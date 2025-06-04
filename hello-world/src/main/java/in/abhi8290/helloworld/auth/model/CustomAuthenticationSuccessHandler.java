package in.abhi8290.helloworld.auth.model;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.*;

import in.abhi8290.helloworld.auth.AuthService;
import jakarta.servlet.http.Cookie;
import org.springframework.security.oauth2.core.user.OAuth2User;
import in.abhi8290.helloworld.user.model.AuthProvider;
import in.abhi8290.helloworld.user.model.User;
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    
    

    @Autowired
    private AuthService authService;

    
    




    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String stateKey = "";


        request.getParameterMap().forEach((k,v)-> System.out.println(k+" "+v));

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("stateKey".equals(cookie.getName())) {
                    stateKey = cookie.getValue();
                    break;
                }
            }
        }
        OAuthStateData stateData = authService.getAndRemoveOAuthState(stateKey);
        
        OAuth2User principal = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = principal.getAttributes();
        String name = (String) attributes.getOrDefault("name", attributes.get("login"));
        String email = (String) attributes.getOrDefault("email", "not provided");


String token = "";
        
        try {
            User user = authService.findOrCreateOAuthUser(name, "", email, AuthProvider.GOOGLE);
             token = authService.getAccessToken(user.getId());
        } catch (Exception e) {
        
            throw new RuntimeException("User Creation Failed ");
            // e.printStackTrace();
        }
   
        if ( email == null) {
            throw new RuntimeException("Invalid state key");
          }

        if (stateData == null) {
            throw new RuntimeException("Invalid state key");
        }

       response.sendRedirect(stateData.getCallbackUrl()+"?token=" + token);
    }
}
