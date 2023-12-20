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
            select student from Student student
                inner join fetch student.group
                inner join student.person person
            where person.id = :personId
            """)
    Optional<Student> findByPersonIdWithGroup(Long personId);

    @Query("""
            select student from Student student
                inner join fetch student.person
                inner join student.group group
            where group.id = :groupId
            """)
    List<Student> findAllByGroupIdWithPerson(Long groupId);

    @Query("""
            select student from Student student
                inner join fetch student.person
                inner join fetch student.group
            where student.id = :id
            """)
    Optional<Student> findByIdWithPersonAndGroup(Long id);

    @Query("""
            select student from Student student
                left join fetch student.taskStudentList
            where student.id = :id
            """)
    Optional<Student> findByIdWithTaskList(Long id);
}
