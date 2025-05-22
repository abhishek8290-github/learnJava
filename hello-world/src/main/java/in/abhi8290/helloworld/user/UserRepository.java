package in.abhi8290.helloworld.user;

import in.abhi8290.helloworld.core.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User, String> {

    User findByEmail(String email);

    boolean existsByEmail(String email);

    User findByFirstName(String firstName);


}
