package com.model.elements.Course;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "questionaire")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Questionaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private String number;
    
    @Column(nullable = false)
    private String score;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    private Module module;

    @OneToMany(
        mappedBy = "questionaire", 
        cascade = CascadeType.ALL, 
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private List<Question> questions = new ArrayList<>();
    
    // Método helper melhorado para Module
    public void setModule(Module module) {
        if (this.module != module) {
            Module oldModule = this.module;
            this.module = module;
            if (oldModule != null && oldModule.getQuestionaires().contains(this)) {
                oldModule.getQuestionaires().remove(this);
            }
            if (module != null && !module.getQuestionaires().contains(this)) {
                module.getQuestionaires().add(this);
            }
        }
    }
    
    // Método helper melhorado para Question
    public void addQuestion(Question question) {
        if (question != null && !questions.contains(question)) {
            questions.add(question);
            question.setQuestionaire(this);
        }
    }

    public void removeQuestion(Question question) {
        if (questions.remove(question)) {
            question.setQuestionaire(null);
        }
    }
}
