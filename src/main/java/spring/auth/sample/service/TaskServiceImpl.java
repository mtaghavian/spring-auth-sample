package spring.auth.sample.service;

import spring.auth.sample.SpringTestApplication;
import spring.auth.sample.etc.BaseResult;
import spring.auth.sample.model.Task;
import spring.auth.sample.model.User;
import spring.auth.sample.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Masoud Taghavian (masoud.taghavian@gmail.com)
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public BaseResult<Task> save(@RequestBody Task task) {
        if (task.getComplete() == null) {
            task.setComplete(false);
        }
        task.setUser(SpringTestApplication.getCurrentUser());
        task = taskRepository.save(task);
        return new BaseResult<>(task, HttpStatus.OK, "");
    }

    @Override
    public BaseResult<Task> toggleComplete(@RequestBody Task task) {
        Task dbTask = taskRepository.findById(task.getId()).get();
        dbTask.setComplete(!dbTask.getComplete());
        dbTask = taskRepository.save(dbTask);
        return new BaseResult<>(dbTask, HttpStatus.OK, "");
    }

    @Override
    public BaseResult<List<Task>> list() {
        User user = SpringTestApplication.getCurrentUser();
        List<Task> list = taskRepository.findAllByUserId(user.getId());
        Collections.sort(list);
        return new BaseResult<>(list, HttpStatus.OK, "");
    }

    @Override
    public BaseResult<Task> delete(@RequestBody Task task) {
        taskRepository.deleteById(task.getId());
        return new BaseResult<>(task, HttpStatus.OK, "");
    }
}
