package spring.auth.sample.repository;

import spring.auth.sample.model.Role;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Masoud Taghavian (masoud.taghavian@gmail.com)
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
}
