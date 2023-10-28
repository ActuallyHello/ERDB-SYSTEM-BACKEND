package com.ustu.erdbsystem.ermodels.store.repos;

import com.ustu.erdbsystem.ermodels.store.models.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelRepo extends JpaRepository<Model, Long> {

    @Query("""
            SELECT m FROM Model m
                INNER JOIN FETCH m.person
            """)
    List<Model> findAllWithPerson();

    @Query("""
            SELECT m FROM Model m
                INNER JOIN FETCH m.person
            WHERE m.id IN :idList
            """)
    List<Model> findAllByIdInRangeWithPerson(List<Long> idList);

    List<Model> findByIdIn(List<Long> idList);
}
