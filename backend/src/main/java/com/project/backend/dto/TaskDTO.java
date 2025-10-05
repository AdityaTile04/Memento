package com.project.backend.dto;

import com.project.backend.entity.TaskPriority;
import com.project.backend.entity.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskDTO(
        UUID id,
        String title,
        String description,
        LocalDateTime dueDate,
        TaskPriority priority,
        TaskStatus status

) {

}
