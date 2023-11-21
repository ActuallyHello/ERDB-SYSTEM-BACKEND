package com.ustu.erdbsystem.tasks.store.repos;

import com.ustu.erdbsystem.tasks.store.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepo extends JpaRepository<Task, Long> {
}
