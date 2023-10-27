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
                INNER JOIN FETCH Group
            WHERE s.person.id = :personId
            """)
    Optional<Student> findByPersonIdWithGroup(Long personId);

    @Query("""
            SELECT s FROM Student s
                INNER JOIN FETCH Person
            WHERE s.group.id = :groupId
            """)
    List<Student> findAllByGroupIdWithPerson(Long groupId);

    @Query("""
            SELECT s FROM Student s
                INNER JOIN FETCH Person
                INNER JOIN FETCH Group
            """)
    Optional<Student> findByIdWithPersonAndGroup(Long id);
}
