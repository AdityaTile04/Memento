package com.project.backend.controllers;

import com.project.backend.dto.TaskDTO;
import com.project.backend.mappers.TaskMapper;
import com.project.backend.services.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/task-lists/{task_list_id}/tasks")
public class TasksController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TasksController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @GetMapping
    public List<TaskDTO> listTasks(@PathVariable UUID task_list_id) {
        return taskService.listTasks( task_list_id )
                .stream()
                .map( taskMapper::toDto )
                .toList();
    }

}
