package com.ustu.erdbsystem.ermodels.store.models;

import com.ustu.erdbsystem.persons.store.models.Person;
import com.ustu.erdbsystem.tasks.store.models.Result;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="model")
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
    @Column(nullable = false)
    private String topic;
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;
    @Column(nullable = false)
    @Builder.Default
    private Boolean isTaskResult = false;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "model",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    @Builder.Default
    @ToString.Exclude
    private List<ModelEntity> modelEntityList = new ArrayList<>();

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @ToString.Exclude
    private Person person;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "model",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    @ToString.Exclude
    private List<Result> resultList = new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Model model = (Model) o;
        return id != null && id.equals(model.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
