package com.model.elements.Course;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "questions")
@Getter
@Setter
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String number;
    
    @Column(nullable = false)
    private String score;
    
    @Column(columnDefinition = "TEXT", nullable = false)
    private String text;
    
    @Column(nullable = false)
    private String type;
    
    @Column(columnDefinition = "TEXT")
    private String answers;
    
    @Column(name = "correct_answers", columnDefinition = "TEXT")
    private String correctAnswers;
    
    @Column(nullable = false)
    private String area;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questionaire_id", nullable = false)
    private Questionaire questionaire;
    
    // Método helper melhorado para relacionamento bidirecional
    public void setQuestionaire(Questionaire questionaire) {
        if (this.questionaire != questionaire) {
            Questionaire oldQuestionaire = this.questionaire;
            this.questionaire = questionaire;
            if (oldQuestionaire != null && oldQuestionaire.getQuestions().contains(this)) {
                oldQuestionaire.getQuestions().remove(this);
            }
            if (questionaire != null && !questionaire.getQuestions().contains(this)) {
                questionaire.getQuestions().add(this);
            }
        }
    }
} 