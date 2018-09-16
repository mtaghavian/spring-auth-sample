package spring.auth.sample.repository;

import spring.auth.sample.model.Task;
import spring.auth.sample.model.User;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Masoud Taghavian (masoud.taghavian@gmail.com)
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    public List<Task> findAllByUserId(UUID id);
}
