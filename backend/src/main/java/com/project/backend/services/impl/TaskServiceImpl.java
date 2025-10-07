package com.project.backend.services.impl;

import com.project.backend.entity.Task;
import com.project.backend.repository.TaskRepo;
import com.project.backend.services.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepo taskRepo;

    public TaskServiceImpl(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    @Override
    public List<Task> listTasks(UUID id) {
        return taskRepo.findByTaskListId( id );
    }
}
