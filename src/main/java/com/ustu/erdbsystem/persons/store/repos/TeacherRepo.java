package com.ustu.erdbsystem.persons.store.repos;

import com.ustu.erdbsystem.persons.store.models.Person;
import com.ustu.erdbsystem.persons.store.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepo extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByPerson(Person person);

    @Query("""
            SELECT teacher FROM Teacher teacher
                INNER JOIN FETCH teacher.person
                INNER JOIN FETCH teacher.position
            WHERE teacher.id = :id
            """)
    Optional<Teacher> findByIdWithPersonAndPosition(Long id);

    @Query("""
            SELECT teacher FROM Teacher teacher
                INNER JOIN FETCH teacher.position
                INNER JOIN teacher.person person
            WHERE person.id = :personId
            """)
    Optional<Teacher> findByPersonIdWithPosition(Long personId);

    @Query("""
            SELECT teacher FROM Teacher teacher
                INNER JOIN FETCH teacher.person
                INNER JOIN teacher.position position
            WHERE position.id = :positionId
            """)
    List<Teacher> findAllByPositionIdWithPerson(Long positionId);

    @Query("""
            SELECT teacher FROM Teacher teacher
                LEFT JOIN FETCH teacher.taskList
            WHERE teacher.id = :id
            """)
    Optional<Teacher> findByIdWithTasks(Long id);

    @Query("""
            SELECT teacher FROM Teacher teacher
                LEFT JOIN FETCH teacher.resultList
            WHERE teacher.id = :id
            """)
    Optional<Teacher> findByIdWithResults(Long id);
}
