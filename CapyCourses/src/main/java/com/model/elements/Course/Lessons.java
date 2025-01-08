package com.model.elements.Course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lessons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lessons {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "module_id")
    private Module module;

    private String title;
    private String videoLink;
    private String details;
    private String materials;
    private String duration;
    private int moduleNumber;
    private int numberOfLesson;

    // Construtor usado atualmente
    public Lessons(String title, String videoLink, String details, String materials, 
            String duration, String moduleTitle, int moduleNumber, int numberOfLesson) {
        this.title = title;
        this.videoLink = videoLink;
        this.details = details;
        this.materials = materials;
        this.duration = duration;
        this.moduleNumber = moduleNumber;
        this.numberOfLesson = numberOfLesson;
    }
}