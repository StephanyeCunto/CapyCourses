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
import lombok.AllArgsConstructor;

@Entity
@Table(name = "modules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private int moduleNumber;
    private String title;
    private String description;
    private String duration;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL)
    private List<Lessons> lessons;

    // Construtor usado atualmente
    public Module(int moduleNumber, String title, String description, String duration, List<Lessons> lessons) {
        this.moduleNumber = moduleNumber;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.lessons = lessons;
    }
}