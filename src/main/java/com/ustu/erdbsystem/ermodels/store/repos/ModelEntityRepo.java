package com.ustu.erdbsystem.ermodels.store.repos;

import com.ustu.erdbsystem.ermodels.store.models.Model;
import com.ustu.erdbsystem.ermodels.store.models.ModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelEntityRepo extends JpaRepository<ModelEntity, Long> {

    @Query("""
           SELECT e FROM ModelEntity e
           INNER JOIN FETCH e.attributeList
           WHERE e.model = :model
           """
    )
    List<ModelEntity> getAllByModel(Model model);
}
