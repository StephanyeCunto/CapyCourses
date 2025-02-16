package com.model.elements.Course;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

  @OneToOne(mappedBy = "questionaire")
  private Module module;

  @OneToMany(
      mappedBy = "questionaire",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.EAGER)
  private List<Question> questions = new ArrayList<>();

  // Método helper para Module
  public void setModule(Module module) {
    if (this.module != module) {
      Module oldModule = this.module;
      this.module = module;

      if (oldModule != null && oldModule.getQuestionaire() == this) {
        oldModule.setQuestionaire(null);
      }
      if (module != null && module.getQuestionaire() != this) {
        module.setQuestionaire(this);
      }
    }
  }

  // Método helper para Question
  public void addQuestion(Question question) {
    if (question == null) {
      throw new IllegalArgumentException("Question cannot be null");
    }

    if (!questions.contains(question)) {
      questions.add(question);
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
