package com.model.elements.Course;

import javax.persistence.*;
import com.model.login_cadastro.Student;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class StudentAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Question question;

    @ManyToOne
    private Questionaire questionaire;

    private String answer;
    private boolean isCorrect;
    private double score;
} 