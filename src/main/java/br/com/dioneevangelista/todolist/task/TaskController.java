package br.com.dioneevangelista.todolist.task;

import br.com.dioneevangelista.todolist.user.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;
    private IUserRepository userRepository;

    @PostMapping("/")
    public TaskModel create(@RequestBody TaskModel taskModel){
        System.out.println("Chegou no controller...");
        var task = taskRepository.save(taskModel);
        return task;
    }


}
