package com.ustu.erdbsystem.persons.store.repos;

import com.ustu.erdbsystem.persons.store.models.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PositionRepo extends JpaRepository<Position, Long> {

    @Query("""
            select position from Position position
                left join fetch position.teacherList
            where position.id = :id
            """)
    Optional<Position> findByIdWithTeachers(Long id);
}
