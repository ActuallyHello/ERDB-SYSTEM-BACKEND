package com.ustu.erdbsystem.tasks.store.repos;

import com.ustu.erdbsystem.ermodels.store.models.Model;
import com.ustu.erdbsystem.tasks.store.models.DenormalizeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DenormalizeModelRepo extends JpaRepository<DenormalizeModel, Long> {

    @Query("""
            select denormalizeModel from DenormalizeModel denormalizeModel
                inner join fetch denormalizeModel.taskList
            where denormalizeModel.model = :model
            """)
    Optional<DenormalizeModel> findByModelIdWithTasks(Model model);
}
