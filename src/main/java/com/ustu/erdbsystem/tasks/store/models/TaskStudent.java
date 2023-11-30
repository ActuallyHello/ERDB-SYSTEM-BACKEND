package com.ustu.erdbsystem.tasks.store.models;

import com.ustu.erdbsystem.persons.store.models.Student;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"student", "task"})
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="task_student")
public class TaskStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(updatable = false)
    @CreatedDate
    private Instant createdAt;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    public void setStudent(Student student) {
        this.student = student;
        student.getTaskStudentList().add(this);
    }

    public void setTask(Task task) {
        this.task = task;
        task.getTaskStudentList().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskStudent taskStudent = (TaskStudent) o;
        return id != null && id.equals(taskStudent.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
