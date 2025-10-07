package com.project.backend.services;

import com.project.backend.entity.Task;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    List<Task> listTasks(UUID id);
}
