package in.abhi8290.helloworld.user;

import in.abhi8290.helloworld.core.base.BaseService;
import org.springframework.stereotype.Service;
import in.abhi8290.helloworld.shared.util.hashUtil;

import java.util.Optional;

@Service
public class UserService extends BaseService<User, String> {

    private final UserRepository userRepository;


    // Spring will inject this automatically
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected UserRepository getRepository() {
        return userRepository;
    }

    public Optional<User> findByEmail(String email) throws Exception {

        User user = userRepository.findByEmail(email);
        return Optional.of(user);
    }


    public Optional<User>  createUser(User user) throws Exception {
        String hashedPassword = hashUtil.getHashedPasswordWithSaltAndAlgorithm(user.getPassword());
        user.setPassword(hashedPassword);
        save(user);
        return Optional.of(user) ;

    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }



}
