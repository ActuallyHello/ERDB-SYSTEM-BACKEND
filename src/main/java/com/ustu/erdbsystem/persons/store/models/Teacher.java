package com.ustu.erdbsystem.persons.store.models;

import com.ustu.erdbsystem.tasks.store.models.Result;
import com.ustu.erdbsystem.tasks.store.models.Task;
import jakarta.persistence.CascadeType;
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

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    private Position position;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "teacher",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<Task> taskList = new ArrayList<>();

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "teacher",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<Result> resultList = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return id != null && id.equals(teacher.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
