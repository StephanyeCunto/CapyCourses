package com.model.elements.Course;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String number;
    private String score;
    private String text;
    private String type;
    private String answers;
    private String correctAnswers;
    private String area;
    
    @ManyToOne
    private Questionaire questionaire;
} 