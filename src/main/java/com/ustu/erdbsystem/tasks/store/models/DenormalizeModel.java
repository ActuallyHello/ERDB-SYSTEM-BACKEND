package com.ustu.erdbsystem.tasks.store.models;

import com.ustu.erdbsystem.ermodels.store.models.Model;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
@ToString(exclude = {"model", "taskList"})
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="denormalize_model")
public class DenormalizeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 2000)
    private String view;
    @LastModifiedDate
    private Instant updatedAt;

    @OneToOne(
            cascade = CascadeType.MERGE,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "model_id", referencedColumnName = "id")
    private Model model;

    @ManyToMany(
            mappedBy = "denormalizeModelList"
    )
    @Builder.Default
    private List<Task> taskList = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DenormalizeModel denormalizeModel = (DenormalizeModel) o;
        return id != null && id.equals(denormalizeModel.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
