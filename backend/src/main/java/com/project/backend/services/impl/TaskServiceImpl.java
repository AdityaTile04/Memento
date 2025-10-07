package com.project.backend.services.impl;

import com.project.backend.entity.Task;
import com.project.backend.entity.TaskList;
import com.project.backend.entity.TaskPriority;
import com.project.backend.entity.TaskStatus;
import com.project.backend.repository.TaskListRepo;
import com.project.backend.repository.TaskRepo;
import com.project.backend.services.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepo taskRepo;
    private final TaskListRepo taskListRepo;

    public TaskServiceImpl(TaskRepo taskRepo, TaskListRepo taskListRepo) {
        this.taskRepo = taskRepo;
        this.taskListRepo = taskListRepo;
    }

    @Override
    public List<Task> listTasks(UUID id) {
        return taskRepo.findByTaskListId( id );
    }

    @Override
    public Task createTask(UUID tasklistId, Task task) {
        if(task.getStatus() != null) {
            throw new IllegalArgumentException("Task Already has a ID");
        }
        if(task.getTitle() == null || task.getTitle().isBlank()) {
            throw new IllegalArgumentException("Task must have a title");
        }

     TaskPriority taskPriority = Optional.ofNullable( task.getPriority())
             .orElse( TaskPriority.MEDIUM );

        TaskStatus taskStatus = TaskStatus.OPEN;

        TaskList taskList = taskListRepo.findById( tasklistId )
                .orElseThrow( () -> new IllegalArgumentException("Invalid task list ID provided") );

        LocalDateTime now = LocalDateTime.now();

        Task saveTask = new Task(
                null,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                taskStatus,
                taskPriority,
                taskList,
                now,
                now
        );

        return taskRepo.save( saveTask );
    }
}
