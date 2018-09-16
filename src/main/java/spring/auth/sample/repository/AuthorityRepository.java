package spring.auth.sample.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.auth.sample.model.Authority;

/**
 * @author Masoud Taghavian (masoud.taghavian@gmail.com)
 */
@Repository
public interface AuthorityRepository extends JpaRepository<Authority, UUID> {
}
