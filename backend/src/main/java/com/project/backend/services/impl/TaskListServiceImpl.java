package com.project.backend.services.impl;

import com.project.backend.entity.TaskList;
import com.project.backend.repository.TaskListRepo;
import com.project.backend.services.TaskListService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskListServiceImpl implements TaskListService {

   private TaskListRepo taskListRepo;

    public TaskListServiceImpl(TaskListRepo taskListRepo) {
        this.taskListRepo = taskListRepo;
    }

    @Override
    public List<TaskList> listTaskList() {
        return taskListRepo.findAll();
    }
}
