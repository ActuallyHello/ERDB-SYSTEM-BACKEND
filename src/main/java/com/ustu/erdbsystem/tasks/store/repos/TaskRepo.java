package com.ustu.erdbsystem.tasks.store.repos;

import com.ustu.erdbsystem.tasks.store.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TaskRepo extends JpaRepository<Task, Long> {

    @Query("""
            select task from Task task
                inner join fetch task.teacher teacher
                inner join fetch teacher.person
                inner join fetch teacher.position
            """)
    List<Task> findAllWithTeachers();

    @Query("""
            select task from Task task
                inner join fetch task.denormalizeModelList
            where task.id = :id
            """
    )
    Optional<Task> findByIdWithDenormalizeModel(Long id);
}
