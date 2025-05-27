package in.abhi8290.helloworld.auth;
import in.abhi8290.helloworld.user.*;
import in.abhi8290.helloworld.auth.dto.*;
import java.util.Optional;
import in.abhi8290.helloworld.shared.util.hashUtil;
import org.springframework.stereotype.Service;
import in.abhi8290.helloworld.shared.TokenService;
import in.abhi8290.helloworld.auth.exception.UserNotFoundException;
import in.abhi8290.helloworld.auth.exception.IncorrectPasswordException;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;

@Service
public class AuthService {

    public final UserService userService;

    TokenService tokenService = new TokenService();


    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public String getAccessToken(String userId) {
        return tokenService.generateAccessToken(userId);
    }


    public LoginResponseDto authenticate(String email, String password) throws Exception {

        Optional<User> currentUser = userService.findByEmail(email);;

        if (currentUser.isEmpty()) {
            throw new UserNotFoundException("User not found with email: " + email);
        }
        boolean correctUser = hashUtil.verifyPassword(password, currentUser.get().getPassword());

        if(!correctUser) throw new IncorrectPasswordException("Invalid Password Provided");

        return new LoginResponseDto("Refresh token ", getAccessToken(currentUser.get().getId()));

    }


    public LoginResponseDto registerUser(RegisterUserDto userToCreate) throws Exception {

        User user = userService.createUser( new User(
                userToCreate.getFirstName(),
                userToCreate.getLastName(),
                userToCreate.getEmail(),
                userToCreate.getPassword()
        ));

        return new LoginResponseDto("Refresh token ", getAccessToken(user.getId()));
    }

    public LoginResponseDto logoutUser(String userId) throws Exception {
        Optional<User> currentUser = userService.findById(userId);
        if (currentUser.isEmpty()) {
            throw new UserNotFoundException("User not found with email: " + userId);
        }


        return new LoginResponseDto("Refresh token ", getAccessToken(currentUser.get().getId()));

    }

}
