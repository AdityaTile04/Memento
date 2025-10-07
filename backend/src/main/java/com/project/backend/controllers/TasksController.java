package com.project.backend.controllers;

import com.project.backend.dto.TaskDTO;
import com.project.backend.entity.Task;
import com.project.backend.mappers.TaskMapper;
import com.project.backend.services.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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

    @PostMapping
    public TaskDTO createTask(@RequestBody TaskDTO taskDTO, @PathVariable UUID task_list_id) {
        Task createdTask = taskService
                .createTask( task_list_id, taskMapper.fromDto( taskDTO ) );

        return taskMapper.toDto( createdTask );
    }

    @GetMapping(path = "/{task_id}")
    public Optional<TaskDTO> getTask(
            @PathVariable("task_list_id") UUID taskListId,
            @PathVariable("task_id") UUID taskId
            ) {
       return taskService.getTask( taskListId, taskId )
                .map( taskMapper::toDto);
    }

    @PutMapping("/{task_id}")
    public TaskDTO updateTask(
            @PathVariable("task_list_id") UUID taskListId,
            @PathVariable("task_id") UUID taskId,
            @RequestBody TaskDTO taskDTO
    ) {
        Task updatedTask = taskService.updateTask( taskListId, taskId, taskMapper.fromDto( taskDTO ) );
        return taskMapper.toDto( updatedTask );
    }

}
