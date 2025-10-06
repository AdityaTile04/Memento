package com.project.backend.mappers.impl;

import com.project.backend.dto.TaskListDTO;
import com.project.backend.entity.Task;
import com.project.backend.entity.TaskList;
import com.project.backend.entity.TaskStatus;
import com.project.backend.mappers.TaskListMapper;
import com.project.backend.mappers.TaskMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskListMapperImpl implements TaskListMapper {

    private final TaskMapper taskMapper;

    public TaskListMapperImpl(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskList fromDto(TaskListDTO taskListDTO) {
        return new TaskList(
                taskListDTO.id(),
                taskListDTO.title(),
                taskListDTO.description(),
                Optional.ofNullable( taskListDTO.task() )
                        .map( tasks ->  tasks.stream().map(taskMapper::fromDto )
                                .toList()
                        ).orElse(null),
                null,
                null
        );
    }

    @Override
    public TaskListDTO toDto(TaskList taskList) {
        return new TaskListDTO(
                taskList.getId(),
                taskList.getTitle(),
                taskList.getDescription(),
                Optional.ofNullable( taskList.getTasks() )
                        .map( List::size )
                        .orElse( 0 ),
                calculateTaskListProgress( taskList.getTasks() ),
                Optional.ofNullable( taskList.getTasks() )
                        .map( tasks -> tasks.stream().map( taskMapper::toDto ).toList()
                        ).orElse( null )
        );
    }

    private Double calculateTaskListProgress(List<Task> tasks) {
        if(null == tasks) {
            return null;
        }

       long closedTaskCount =  tasks.stream().filter( task ->
               TaskStatus.CLOSED == task.getStatus()).count();

        return (double) closedTaskCount / tasks.size();
    }
}
