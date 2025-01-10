package com.model.elements.Course;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Questionaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    private String number;
    private String score;
    private String description;
    
    @ManyToOne
    private Module module;
}
