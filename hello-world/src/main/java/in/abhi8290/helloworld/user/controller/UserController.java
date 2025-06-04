package in.abhi8290.helloworld.user.controller;
import in.abhi8290.helloworld.shared.TokenService;
import in.abhi8290.helloworld.user.service.UserService;
import in.abhi8290.helloworld.user.dto.UserRequestDto;
import in.abhi8290.helloworld.user.mapper.UserMapper;
import in.abhi8290.helloworld.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.annotation.AuthenticationPrincipal;


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


    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserRequestDto request) throws Exception {
        logger.info("Creating user with email: {}", request.getEmail());
        User user = UserMapper.toEntity(request);
        User _created = userService.createUser(user);
        return ResponseEntity.ok(_created);
    }
    @GetMapping("/me")
    public ResponseEntity<User> getAuthenticatedUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(user);
    }
}
