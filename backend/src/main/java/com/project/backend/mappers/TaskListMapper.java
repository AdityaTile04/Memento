package com.project.backend.mappers;

import com.project.backend.dto.TaskListDTO;
import com.project.backend.entity.TaskList;

public interface TaskListMapper {

    TaskList fromDto(TaskListDTO taskListDTO);

   TaskListDTO toDto(TaskList taskList);
}
