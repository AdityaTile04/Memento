package com.project.backend.services.impl;

import com.project.backend.entity.TaskList;
import com.project.backend.repository.TaskListRepo;
import com.project.backend.services.TaskListService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Override
    public TaskList createTaskList(TaskList taskList) {
        if(null == taskList) {
            throw new IllegalArgumentException("Task list already has a ID");
        }
        if(null == taskList.getTitle() || taskList.getTitle().isBlank()) {
            throw new IllegalArgumentException("Task list title must be present");
        }

        LocalDateTime now = LocalDateTime.now();

      return taskListRepo.save( new TaskList(
              null,
              taskList.getTitle(),
              taskList.getDescription(),
              null,
              now,
              now
      ) );
    }
}
