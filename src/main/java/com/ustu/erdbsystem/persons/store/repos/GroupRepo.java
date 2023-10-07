package com.ustu.erdbsystem.persons.store.repos;

import com.ustu.erdbsystem.persons.store.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GroupRepo extends JpaRepository<Group, Long> {
    List<Group> findByIsActive(@Param("isActive") Boolean isActive);
}
