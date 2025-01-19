package com.model.student;

import java.time.LocalDate;
import javax.persistence.*;
import lombok.Data;

import com.model.login_cadastro.Student;
import com.model.elements.Course.Course;

@Entity
@Table(name = "student_courses")
@Data
public class StudentCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column
    private LocalDate completionDate;

    @Column
    private Integer progress;

    @Column
    private String status; // "not_started", "in_progress", "completed"

    @Column
    private Double grade;
} 