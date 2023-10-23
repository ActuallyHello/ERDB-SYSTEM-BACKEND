package com.ustu.erdbsystem.ermodels.store.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"attributeList", "model"})
@Entity
@Table(name="entity")
public class ModelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    private Model model;

    @OneToMany(
            mappedBy = "modelEntity",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<Attribute> attributeList = new ArrayList<>();

    public void addAttribute(Attribute attribute) {
        if (attribute == null) throw new IllegalArgumentException("Attribute is null!");
        this.attributeList.add(attribute);
        attribute.setModelEntity(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelEntity modelEntity = (ModelEntity) o;
        return id != null && id.equals(modelEntity.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
