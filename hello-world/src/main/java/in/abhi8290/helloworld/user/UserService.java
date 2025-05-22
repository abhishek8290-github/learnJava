package in.abhi8290.helloworld.user;

import in.abhi8290.helloworld.core.base.BaseService;
import org.springframework.stereotype.Service;

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

    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
}
