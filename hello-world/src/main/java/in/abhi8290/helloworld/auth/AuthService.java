package in.abhi8290.helloworld.auth;
import in.abhi8290.helloworld.user.*;

import java.util.Optional;
import in.abhi8290.helloworld.shared.util.hashUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthService {


    public final UserService userService;



    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public Optional<User> authenticate(String email, String password) throws Exception {
        Optional<User> user = userService.findByEmail(email);
        if (user.isEmpty())  throw new Exception("User Not Found");
        boolean correctUser = hashUtil.verifyPassword(password, user.get().getPassword());

        if(!correctUser) {
            throw new Exception("Incorrect Password");
        }
        return user ;
    }
}
