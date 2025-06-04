package in.abhi8290.helloworld.shared;



import in.abhi8290.helloworld.user.model.User;
import in.abhi8290.helloworld.user.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.stereotype.Component;

@Component
public class JwtFilter extends OncePerRequestFilter {



    @Autowired
    protected UserService userService;

    private static final String[] EXCLUDED_PATHS = {
        "/auth/login",
        "/auth/register",
        "/auth/forgot-password",
        "/auth/reset-password",
        "/oauth2",
        "/swagger",
        "/v3/api-docs",
        "/swagger-ui",
        "/swagger-ui.html"
    };


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain
    )throws jakarta.servlet.ServletException, java.io.IOException {
        String path = request.getRequestURI();

    for (String excluded : EXCLUDED_PATHS) {
        if (path.startsWith(excluded)) {
            filterChain.doFilter(request, response);
            return;
        }
    }
    
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            try {
                String userId = TokenService.validateAccessToken(token);
                User user = userService.findUserById(userId);
                System.out.println("userId = " + userId);

                    UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                
            } catch (Exception e) {
                throw new Error("Invalid token");
            }
        }
        else throw new Error("Invalid token");
        filterChain.doFilter(request, response);
    }
}
