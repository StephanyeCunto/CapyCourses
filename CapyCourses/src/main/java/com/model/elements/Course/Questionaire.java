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
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "module_id")
    private Module module;

    @OneToMany(
        mappedBy = "questionaire", 
        cascade = {CascadeType.PERSIST, CascadeType.MERGE}, // Modificado aqui
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private List<Question> questions = new ArrayList<>();
    
    // Método helper para Module modificado para ser mais seguro
    public void setModule(Module module) {
        if (module == null) {
            throw new IllegalArgumentException("Module cannot be null");
        }
        
        if (this.module != module) {
            Module oldModule = this.module;
            this.module = module;
            
            if (oldModule != null) {
                oldModule.getQuestionaires().remove(this);
            }
            if (!module.getQuestionaires().contains(this)) {
                module.getQuestionaires().add(this);
            }
        }
    }
    
    // Método helper para Question melhorado
    public void addQuestion(Question question) {
        if (question == null) {
            throw new IllegalArgumentException("Question cannot be null");
        }
        
        if (!this.questions.contains(question)) {
            this.questions.add(question);
            question.setQuestionaire(this);
        }
    }

    public void removeQuestion(Question question) {
        if (question == null) {
            return;
        }
        
        if (this.questions.remove(question)) {
            question.setQuestionaire(null);
        }
    }
    
    // Método de inicialização para garantir que a lista nunca seja null
    @PostLoad
    private void initializeCollections() {
        if (questions == null) {
            questions = new ArrayList<>();
        }
    }
    
    // Equals e HashCode baseados no ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Questionaire)) return false;
        Questionaire that = (Questionaire) o;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
