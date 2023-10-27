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
            SELECT t FROM Teacher t
                INNER JOIN FETCH t.person
                INNER JOIN FETCH t.position
            WHERE t.id = :id
            """)
    Optional<Teacher> findByIdWithPersonAndPosition(Long id);

    @Query("""
            SELECT t FROM Teacher t
                INNER JOIN FETCH t.position
                INNER JOIN t.person pers
            WHERE pers.id = :personId
            """)
    Optional<Teacher> findByPersonIdWithPosition(Long personId);

    @Query("""
            SELECT t FROM Teacher t
                INNER JOIN FETCH t.person
                INNER JOIN t.position pos
            WHERE pos.id = :positionId
            """)
    List<Teacher> findAllByPositionIdWithPerson(Long positionId);
}
