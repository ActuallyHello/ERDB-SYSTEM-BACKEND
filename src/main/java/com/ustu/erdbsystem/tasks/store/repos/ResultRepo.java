package com.ustu.erdbsystem.tasks.store.repos;

import com.ustu.erdbsystem.persons.store.models.Person;
import com.ustu.erdbsystem.tasks.store.models.Result;
import com.ustu.erdbsystem.tasks.store.models.Task;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ResultRepo extends JpaRepository<Result, Long> {

    @Query("""
            select result from Result result
                inner join fetch result.task
                inner join fetch result.model
                left join fetch result.teacher
            """)
    List<Result> findAllWithTaskAndModelAndTeacher(Pageable pageable);

    @Query("""
            select result from Result result
                inner join result.model model
            where result.task = :task and model.person = :person
            order by result.updatedAt desc
            limit 1
            """)
    Optional<Result> findLastByPersonAndTask(Person person, Task task);

    @Query("""
            select result from Result result
                inner join fetch result.task task
                    left join fetch task.denormalizeModelList dm
                        left join fetch dm.model
                left join fetch result.model
                left join fetch result.teacher
            where result.id = :id
            """)
    Optional<Result> findByIdWithModelAndTaskAndTeacher(Long id);

}
