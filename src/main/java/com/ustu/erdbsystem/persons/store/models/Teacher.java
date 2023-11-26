package com.ustu.erdbsystem.persons.store.models;

import com.ustu.erdbsystem.tasks.store.models.Result;
import com.ustu.erdbsystem.tasks.store.models.Task;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"position", "taskList", "resultList", "person"})
@Entity
@Table(name="teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(
            fetch = FetchType.EAGER,
            optional = false
    )
    private Position position;

    @OneToMany(
            mappedBy = "teacher",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<Task> taskList = new ArrayList<>();

    public void addTask(Task task) {
        this.taskList.add(task);
        task.setTeacher(this);
    }

    @OneToMany(
            mappedBy = "teacher",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<Result> resultList = new ArrayList<>();

    public void addResult(Result result) {
        this.resultList.add(result);
        result.setTeacher(this);
    }

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

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
