package com.ustu.erdbsystem.tasks.store.models;

import com.ustu.erdbsystem.ermodels.store.models.Model;
import com.ustu.erdbsystem.persons.store.models.Teacher;
import com.ustu.erdbsystem.tasks.store.models.enums.Mark;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"model", "teacher"})
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="result")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Mark mark;
    @Column(updatable = false)
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;

    @ManyToOne(
            fetch = FetchType.EAGER,
            optional = false
    )
    private Model model;

    public void setModel(Model model) {
        this.model = model;
        model.getResultList().add(this);
    }

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    private Teacher teacher;

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
        teacher.getResultList().add(this);
    }

    @ManyToOne(
            fetch = FetchType.EAGER,
            optional = false
    )
    private Task task;

    public void setTask(Task task) {
        this.task = task;
        task.getResultList().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return id != null && id.equals(result.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
