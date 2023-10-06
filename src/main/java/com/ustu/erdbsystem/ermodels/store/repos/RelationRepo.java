package com.ustu.erdbsystem.ermodels.store.repos;

import com.ustu.erdbsystem.ermodels.store.models.Relation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelationRepo extends JpaRepository<Relation, Long> {

    @Query(
            value = """
                        SELECT r.id, r.power, r.entity1, r.entity2
                        FROM relation as r
                        WHERE r.entity1 IN (:idList) OR r.entity2 IN (:idList)
                    """,
            nativeQuery = true
    )
    List<Relation> findByModelEntity1IdOrModelEntity2IdInModelEntityIdList(@Param("idList") List<Long> modelEntityIdList);
}
