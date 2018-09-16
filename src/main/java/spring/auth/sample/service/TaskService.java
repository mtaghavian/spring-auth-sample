package spring.auth.sample.service;

import spring.auth.sample.etc.BaseResult;
import spring.auth.sample.model.Task;
import spring.auth.sample.model.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Masoud Taghavian (masoud.taghavian@gmail.com)
 */
@RestController
@RequestMapping(value = "/task")
public interface TaskService {

    @PostMapping(value = "/list")
    @PreAuthorize("hasAuthority('TASK_READ_WRITE')")
    public BaseResult<List<Task>> list();

    @PostMapping(value = "/save")
    @PreAuthorize("hasAuthority('TASK_READ_WRITE')")
    public BaseResult<Task> save(@RequestBody Task task);

    @PostMapping(value = "/delete")
    @PreAuthorize("hasAuthority('TASK_READ_WRITE')")
    public BaseResult<Task> delete(@RequestBody Task task);

    @PostMapping(value = "/toggleComplete")
    @PreAuthorize("hasAuthority('TASK_READ_WRITE')")
    public BaseResult<Task> toggleComplete(@RequestBody Task task);
}
