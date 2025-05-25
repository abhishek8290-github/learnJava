package in.abhi8290.helloworld.user;

import in.abhi8290.helloworld.shared.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import in.abhi8290.helloworld.user.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final TokenService tokenService;


    // Spring will inject this for you
    public UserController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    // GET all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/me")
    public User getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7); // Remove "Bearer "
        String userId = tokenService.validateAccessToken(token); // Will throw if invalid

        return userService.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found for token"));
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {



        return userService.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    // GET user by email
    @GetMapping("/by-email")
    public Optional<User> getUserByEmail(@RequestParam String email) throws Exception {
        return userService.findByEmail(email);
    }



    // POST create a user
    @PostMapping
    public Optional<User> createUser(@RequestBody User user) throws Exception {
        logger.info("Creating user with email: {}", user.getEmail());

        return userService.createUser(user);
    }
}
