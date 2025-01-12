package com.model.elements.Course;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import lombok.NoArgsConstructor;
import javax.persistence.Column;


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

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lessons> lessons = new ArrayList<>();
    
    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true) 
    private List<Questionaire> questionaires = new ArrayList<>();
    
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
    
    // Método helper melhorado para Questionaire
    public void addQuestionaire(Questionaire questionaire) {
        if (questionaire != null && !questionaires.contains(questionaire)) {
            questionaires.add(questionaire);
            questionaire.setModule(this);
        }
    }

    // Métodos para remover relacionamentos
    public void removeLesson(Lessons lesson) {
        if (lessons.remove(lesson)) {
            lesson.setModule(null);
        }
    }

    public void removeQuestionaire(Questionaire questionaire) {
        if (questionaires.remove(questionaire)) {
            questionaire.setModule(null);
        }
    }
}