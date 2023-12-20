package com.ustu.erdbsystem.persons.store.repos;

import com.ustu.erdbsystem.persons.store.models.Person;
import com.ustu.erdbsystem.persons.store.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepo extends JpaRepository<Person, Long> {

    @Query("""
            select person from Person person
                left join Student student
                    on student.person = person
                left join Teacher teacher
                    on teacher.person = person
            where (student.person is null or student.person != person)
                  and
                  (teacher.person is null or teacher.person != person)
            """)
    List<Person> findAllNotUsed();

    Optional<Person> findByUser(User user);

    @Query("""
            select p from Person p
                left join fetch p.modelList
            where p.id = :id
            """)
    Optional<Person> findByIdWithModels(Long id);
}
