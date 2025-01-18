package com.model.elements.Course;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "questions")
@Getter
@Setter
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    @Column(name = "answers")
    private String answers;

    @Column(name = "correct_answers")
    private String correctAnswers;

    @Column(nullable = false)
    private String type;

    private String score;

    private String number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questionaire_id", nullable = false)
    private Questionaire questionaire;

    @Column(name = "expected_answer")
    private String expectedAnswer;

    @Column(name = "evaluation_criteria")
    private String evaluationCriteria;

    @Column(name = "multiple_correct_answers")
    private String multipleCorrectAnswers;

    // Adicionar construtor vazio
    public Question() {}

    @PrePersist
    public void prePersist() {
        if (this.number == null) {
            this.number = "1"; // ou alguma lógica para gerar o número
        }
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", answers='" + answers + '\'' +
                ", correctAnswers='" + correctAnswers + '\'' +
                ", type='" + type + '\'' +
                ", score='" + score + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
} 