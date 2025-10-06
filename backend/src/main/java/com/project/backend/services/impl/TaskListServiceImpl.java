package com.project.backend.services.impl;

import com.project.backend.entity.TaskList;
import com.project.backend.repository.TaskListRepo;
import com.project.backend.services.TaskListService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

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

    @Override
    public Optional<TaskList> getTaskList(UUID id) {
        return taskListRepo.findById( id );
    }

    @Override
    public TaskList updateTaskList(UUID id, TaskList taskList) {
        if(null == taskList.getId()) {
            throw new IllegalArgumentException("Task list must have an ID");
        }

        if(!Objects.equals( taskList.getId(), id )) {
            throw new IllegalArgumentException("Attempting to change task list ID, this is not permitted");
        }

       TaskList existingTaskList =  taskListRepo.findById( id )
                .orElseThrow(() -> new IllegalArgumentException("Task list not found"));

        existingTaskList.setTitle( taskList.getTitle() );
        existingTaskList.setDescription( taskList.getDescription() );
        existingTaskList.setUpdated( LocalDateTime.now() );
        return taskListRepo.save( existingTaskList );
    }

    @Override
    public void deleteTaskList(UUID id) {
        taskListRepo.deleteById( id );
    }
}
