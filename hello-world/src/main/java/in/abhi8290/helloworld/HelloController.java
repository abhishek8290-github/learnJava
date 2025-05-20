package in.abhi8290.helloworld;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

// Defining
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
 class UserRequestDTO {
    private User user;

    // getters and setters
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public static class User {
        private Name name;

        // getters and setters
        public Name getName() { return name; }
        public void setName(Name name) { this.name = name; }
    }

    public static class Name {
        private String first_name;
        private String last_name;

        // getters and setters
        public String getFirst_name() { return first_name; }
        public void setFirst_name(String first_name) { this.first_name = first_name; }

        public String getLast_name() { return last_name; }
        public void setLast_name(String last_name) { this.last_name = last_name; }
    }
}



// DTO class
class LoginRequest {
    private String username;
    private String password;

    // Constructors
    public LoginRequest() {
    }

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


@RestController
public class HelloController {
    // Defining
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/")
    public String hello() {

        logger.info("Wait I have got a new Logger man !!");
        return "Hello, World!  ";
    }

    @GetMapping("/v1")
    public String helloV1() {
        return "Hello, World2 ";
    }

    @PostMapping("/")
    public UserRequestDTO echoBody(@RequestBody UserRequestDTO request) {
        logger.info("Received username: {}, password: {}", request.getUser().getName().getFirst_name(), request.getUser().getName().getLast_name());
        return request; // returns same object as response
    }
}
