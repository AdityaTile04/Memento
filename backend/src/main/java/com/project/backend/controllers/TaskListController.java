package com.project.backend.controllers;

import com.project.backend.dto.TaskListDTO;
import com.project.backend.entity.TaskList;
import com.project.backend.mappers.TaskListMapper;
import com.project.backend.services.TaskListService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/task-lists")
@CrossOrigin(origins = "http://localhost:5173")
public class TaskListController {

    private final TaskListService taskListService;
    private final TaskListMapper taskListMapper;

    public TaskListController(TaskListService taskListService, TaskListMapper taskListMapper) {
        this.taskListService = taskListService;
        this.taskListMapper = taskListMapper;
    }

    @GetMapping
    public List<TaskListDTO> listTaskLists() {
       return taskListService.listTaskList()
                .stream()
                .map( taskListMapper::toDto )
                .toList();
    }

    @PostMapping
    public TaskListDTO addTaskList(@RequestBody TaskListDTO taskListDTO) {
        TaskList createdTaskList = taskListService.createTaskList(
                taskListMapper.fromDto( taskListDTO )
        );
        return taskListMapper.toDto( createdTaskList );
    }

    @GetMapping("/{task_list_id}")
    public Optional<TaskListDTO> getTaskList(@PathVariable(name = "task_list_id") UUID taskListId) {
        return taskListService.getTaskList( taskListId ).map( taskListMapper::toDto );
    }

    @PutMapping("/{task_list_id}")
    public TaskListDTO updatedTask(
            @PathVariable("task_list_id") UUID taskListId,
            @RequestBody TaskListDTO taskListDTO
    ) {
        TaskList updatedTaskList = taskListService.updateTaskList(
                taskListId,
                taskListMapper.fromDto( taskListDTO )
        );

        return taskListMapper.toDto( updatedTaskList );
    }

    @DeleteMapping("/{id}")
    public void deleteTaskList(@PathVariable UUID id) {
        taskListService.deleteTaskList( id );
    }

}
