package in.abhi8290.helloworld.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import in.abhi8290.helloworld.user.User;
import in.abhi8290.helloworld.auth.dto.*;





@RestController
@RequestMapping("/auth")
public class AuthController {

    public static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Optional<User> login(@RequestBody LoginRequestDto request) throws Exception {
        return authService.authenticate(request.getUsername(), request.getPassword());
    }
}
