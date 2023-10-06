package com.ustu.erdbsystem.ermodels.store.models;

import com.ustu.erdbsystem.ermodels.store.models.enums.Power;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="relation")
public class Relation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Power power;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="entity1")
    private ModelEntity modelEntity1;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="entity2")
    private ModelEntity modelEntity2;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Relation relation = (Relation) o;
        return id != null && id.equals(relation.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
