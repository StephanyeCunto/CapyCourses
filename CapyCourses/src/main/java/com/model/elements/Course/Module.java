package com.model.elements.Course;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "modules")
@Getter
@Setter
@NoArgsConstructor
public class Module {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "course_id", nullable = false)
  private Course course;

  @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Set<Lessons> lessons = new HashSet<>();

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "questionaire_id")
  private Questionaire questionaire;

  @Column(nullable = false)
  private int moduleNumber;

  @Column(nullable = false)
  private String title;

  @Column(length = 1000)
  private String description;

  private String duration;

  // Construtor para dados básicos
  public Module(int moduleNumber, String title, String description, String duration) {
    this.moduleNumber = moduleNumber;
    this.title = title;
    this.description = description;
    this.duration = duration;
  }

  // Método helper melhorado para Course
  public void setCourse(Course course) {
    if (this.course != course) {
      Course oldCourse = this.course;
      this.course = course;
      if (oldCourse != null && oldCourse.getModules().contains(this)) {
        oldCourse.getModules().remove(this);
      }
      if (course != null && !course.getModules().contains(this)) {
        course.getModules().add(this);
      }
    }
  }

  // Método helper melhorado para Lessons
  public void addLesson(Lessons lesson) {
    if (lesson != null && !lessons.contains(lesson)) {
      lessons.add(lesson);
      lesson.setModule(this);
    }
  }

  // Métodos para remover relacionamentos
  public void removeLesson(Lessons lesson) {
    if (lessons.remove(lesson)) {
      lesson.setModule(null);
    }
  }

  public void setQuestionaire(Questionaire questionaire) {
    if (this.questionaire != questionaire) {
      if (this.questionaire != null) {
        this.questionaire.setModule(null);
      }
      this.questionaire = questionaire;
      if (questionaire != null) {
        questionaire.setModule(this);
      }
    }
  }
}
