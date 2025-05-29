package in.abhi8290.helloworld.user;

import in.abhi8290.helloworld.auth.exception.UserNotFoundException;
import in.abhi8290.helloworld.core.base.BaseService;
import in.abhi8290.helloworld.user.exception.InvalidEmailProvidedException;
import in.abhi8290.helloworld.user.exception.UserAlreadyExistsException;
import org.springframework.stereotype.Service;
import in.abhi8290.helloworld.shared.util.hashUtil;

import java.util.Optional;
import org.apache.commons.validator.routines.EmailValidator;

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

  public User createUser(User user) throws Exception {

    if (userRepository.existsByEmail(user.getEmail())) {
      throw new UserAlreadyExistsException("User Already Exists");
    }

    EmailValidator emailValidator = EmailValidator.getInstance();

    boolean isValidEmail = emailValidator.isValid(user.getEmail());

    if (!isValidEmail) {
      throw new InvalidEmailProvidedException("Inavalid Email Provided ");
    }

    String hashedPassword = hashUtil.getHashedPasswordWithSaltAndAlgorithm(user.getPassword());
    user.setPassword(hashedPassword);
    save(user);
    return user;

  }

  public boolean emailExists(String email) {
    return userRepository.existsByEmail(email);
  }

  public User findUserById(String Id) throws Exception {
    Optional<User> user = userRepository.findById(Id);

    if (user.isEmpty())
      throw new UserNotFoundException("User Not Found");

    return user.get();

  }

}
