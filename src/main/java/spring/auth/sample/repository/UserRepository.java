package spring.auth.sample.repository;

import spring.auth.sample.model.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Masoud Taghavian (masoud.taghavian@gmail.com)
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    public User findByUsername(String username);

    
}
