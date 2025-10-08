package com.project.backend.services.impl;

import com.project.backend.entity.Task;
import com.project.backend.entity.TaskList;
import com.project.backend.entity.TaskPriority;
import com.project.backend.entity.TaskStatus;
import com.project.backend.repository.TaskListRepo;
import com.project.backend.repository.TaskRepo;
import com.project.backend.services.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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

    @Transactional
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

    @Override
    public Optional<Task> getTask(UUID taskListId, UUID taskId) {
        return taskRepo.findByTaskListIdAndId( taskListId, taskId );
    }

    @Transactional
    @Override
    public Task updateTask(UUID taskListId, UUID taskId, Task task) {
        if(task.getId() == null) {
            throw new IllegalArgumentException("Task must have an ID");
        }
        if(!Objects.equals(taskId, task.getId())) {
            throw new IllegalArgumentException("Task IDs do not match");
        }
        if(task.getPriority() == null) {
            throw new IllegalArgumentException("Task must have a valid priority");
        }
        if(task.getStatus() == null) {
            throw new IllegalArgumentException("Task must have valid status");
        }

        Task existingTask = taskRepo.findByTaskListIdAndId( taskListId, taskId )
                .orElseThrow( () -> new IllegalArgumentException("Task not found") );

        existingTask.setTitle( task.getTitle() );
        existingTask.setDescription( task.getDescription() );
        existingTask.setDueDate( task.getDueDate() );
        existingTask.setPriority( task.getPriority() );
        existingTask.setStatus( task.getStatus() );
        existingTask.setUpdated( LocalDateTime.now() );

        return taskRepo.save( existingTask );

    }

    @Transactional
    @Override
    public void deleteTask(UUID taskListId, UUID taskId) {
        taskRepo.deleteByTaskListIdAndId( taskListId,taskId );
    }
}
