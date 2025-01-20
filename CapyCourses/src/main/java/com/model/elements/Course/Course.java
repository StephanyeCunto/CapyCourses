package com.model.elements.Course;

//import jakarta.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import com.model.login_cadastro.Teacher;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "courses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Module> modules = new HashSet<>();
    
    public void addModule(Module module) {
        modules.add(module);
        module.setCourse(this);
    }
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String title;
    
    @Column(length = 1000)
    private String description;
    
    @Column(nullable = false)
    private String categoria;
    
    @Column(nullable = false)
    private String nivel;
    
    @Column
    private double rating;
    
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private CourseSettings courseSettings;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    public Course(String title, String description, String instructor, String duration, String level, double rating) {
        this.title = title;
        this.description = description;
        this.name = instructor;
        this.categoria = duration;
        this.nivel = level;
        this.rating = rating;
    }

    public CourseSettings getCourseSettings() {
        return courseSettings;
    }

    public void setCourseSettings(CourseSettings settings) {
        if (settings == null) {
            if (this.courseSettings != null) {
                this.courseSettings.setCourse(null);
            }
        } else {
            settings.setCourse(this);
        }
        this.courseSettings = settings;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
