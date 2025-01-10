package com.model.elements.Course;

//import jakarta.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

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
    
    @OneToOne(cascade = CascadeType.ALL)
    private CourseSettings courseSettings;

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

    public void setCourseSettings(CourseSettings courseSettings) {
        this.courseSettings = courseSettings;
    }
}
