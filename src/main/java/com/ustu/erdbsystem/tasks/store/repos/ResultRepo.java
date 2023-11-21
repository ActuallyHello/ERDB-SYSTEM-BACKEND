package com.ustu.erdbsystem.tasks.store.repos;

import com.ustu.erdbsystem.tasks.store.models.Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepo extends JpaRepository<Result, Long> {
}
