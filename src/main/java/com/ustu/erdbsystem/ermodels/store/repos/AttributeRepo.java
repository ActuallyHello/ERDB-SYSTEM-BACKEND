package com.ustu.erdbsystem.ermodels.store.repos;

import com.ustu.erdbsystem.ermodels.store.models.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeRepo extends JpaRepository<Attribute, Long> {
}
