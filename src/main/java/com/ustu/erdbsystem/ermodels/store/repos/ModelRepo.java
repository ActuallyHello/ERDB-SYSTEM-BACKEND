package com.ustu.erdbsystem.ermodels.store.repos;

import com.ustu.erdbsystem.ermodels.store.models.Model;
import com.ustu.erdbsystem.persons.store.models.Person;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelRepo extends JpaRepository<Model, Long> {

    List<Model> findAllByPerson(Person person);

    @Query("""
            select model from Model model
                inner join fetch model.person person
            where person.personType = com.ustu.erdbsystem.persons.store.models.enums.PersonType.TEACHER
            """)
    List<Model> findAllByTeachers(Pageable pageable);

    @Query("""
            select model from Model model
                inner join fetch model.person person
            where model.isTaskResult = isTaskResult
            """)
    List<Model> findAllByIsTaskResult(Boolean isTaskResult, Pageable pageable);
}
