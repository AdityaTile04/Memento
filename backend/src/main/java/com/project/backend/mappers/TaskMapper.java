package com.project.backend.mappers;

import com.project.backend.dto.TaskDTO;
import com.project.backend.entity.Task;

public interface TaskMapper {

    Task fromDto(TaskDTO taskDTO);

    TaskDTO toDto(Task task);

}
