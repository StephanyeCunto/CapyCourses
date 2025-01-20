package com.model.student;

import java.time.LocalDate;
import javax.persistence.*;
import lombok.Data;

import com.model.login_cadastro.Student;
import com.model.elements.Course.Course;
import java.util.List;
import java.util.ArrayList;

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

    @OneToMany(mappedBy = "studentCourse", cascade = CascadeType.ALL)
    private List<LessonProgress> lessonProgresses = new ArrayList<>();

    @Column
    private Integer completedLessons = 0;

    @Column
    private Integer totalLessons = 0;

    @Column
    private Integer completedQuestionaires = 0;

    @Column
    private Integer totalQuestionaires = 0;

    public double calculateProgress() {
        if (totalLessons == null || totalLessons == 0) {
            return 0.0;
        }
        if (completedLessons == null) {
            completedLessons = 0;
        }
        return (completedLessons * 100.0) / totalLessons;
    }

    public void updateProgress() {
        if (lessonProgresses == null) {
            this.completedLessons = 0;
            return;
        }
        
        // Atualizar número de aulas completadas
        this.completedLessons = (int) lessonProgresses.stream()
            .filter(LessonProgress::isCompleted)
            .count();
        
        // Calcular progresso em porcentagem
        if (totalLessons != null && totalLessons > 0) {
            this.progress = (int) ((completedLessons * 100.0) / totalLessons);
        } else {
            this.progress = 0;
        }
        
        // Verificar se o curso foi completado
        if (completedLessons.equals(totalLessons) && totalLessons > 0) {
            this.status = "completed";
            this.completionDate = LocalDate.now();
        }
    }
} 