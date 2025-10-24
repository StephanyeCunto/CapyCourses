package com.model.student;

import com.model.elements.Course.Lessons;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "lesson_progress")
@Data
public class LessonProgress {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "student_course_id", nullable = false)
  private StudentCourse studentCourse;

  @ManyToOne
  @JoinColumn(name = "lesson_id", nullable = false)
  private Lessons lesson;

  @Column(nullable = false)
  private boolean completed;

  @Column private java.time.LocalDateTime completionDate;
}
