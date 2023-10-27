package com.ustu.erdbsystem.persons.store.repos;

import com.ustu.erdbsystem.persons.store.models.Group;
import com.ustu.erdbsystem.persons.store.models.Person;
import com.ustu.erdbsystem.persons.store.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {

    @Query("""
            SELECT s FROM Student s
                INNER JOIN FETCH s.group
                INNER JOIN s.person p
            WHERE p.id = :personId
            """)
    Optional<Student> findByPersonIdWithGroup(Long personId);

    @Query("""
            SELECT s FROM Student s
                INNER JOIN FETCH s.person
                INNER JOIN s.group g
            WHERE g.id = :groupId
            """)
    List<Student> findAllByGroupIdWithPerson(Long groupId);

    @Query("""
            SELECT s FROM Student s
                INNER JOIN FETCH s.person
                INNER JOIN FETCH s.group
            WHERE s.id = :id
            """)
    Optional<Student> findByIdWithPersonAndGroup(Long id);
}
