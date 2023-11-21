package com.ustu.erdbsystem.tasks.store.repos;

import com.ustu.erdbsystem.tasks.store.models.TaskStudent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskStudentRepo extends JpaRepository<TaskStudent, Long> {
}
