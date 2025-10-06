package com.project.backend.controllers;

import com.project.backend.dto.TaskListDTO;
import com.project.backend.mappers.TaskListMapper;
import com.project.backend.services.TaskListService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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


}
