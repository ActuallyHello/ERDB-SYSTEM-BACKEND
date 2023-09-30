package com.ustu.erdbsystem.tasks.store.repos;

import com.ustu.erdbsystem.tasks.store.models.DenormalizeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DenormalizeModelRepo extends JpaRepository<DenormalizeModel, Long> {
}
