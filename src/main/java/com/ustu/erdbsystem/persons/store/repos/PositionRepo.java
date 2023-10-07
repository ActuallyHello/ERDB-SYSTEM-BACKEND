package com.ustu.erdbsystem.persons.store.repos;

import com.ustu.erdbsystem.persons.store.models.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepo extends JpaRepository<Position, Long> {
}
