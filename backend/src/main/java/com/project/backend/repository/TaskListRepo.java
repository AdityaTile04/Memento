package com.project.backend.repository;

import com.project.backend.entity.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskListRepo extends JpaRepository<TaskList, UUID> {

}
