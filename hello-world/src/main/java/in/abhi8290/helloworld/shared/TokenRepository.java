package in.abhi8290.helloworld.shared;

import in.abhi8290.helloworld.core.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends BaseRepository<Token, String> {

}
