package com.ustu.erdbsystem.persons.store.repos;

import com.ustu.erdbsystem.persons.store.models.Person;
import com.ustu.erdbsystem.persons.store.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepo extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByPerson(Person person);
}
