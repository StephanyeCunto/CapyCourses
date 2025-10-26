package com.model.elements.Course;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "lessons")
@Getter
@Setter
@NoArgsConstructor
public class Lessons {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "module_id", nullable = false)
  private Module module;

  @Column(nullable = false)
  private String title;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(name = "video_link")
  private String videoLink;

  @Column(name = "materials")
  private String materials;

  private String duration;

  @Column(name = "numberOfLesson", nullable = false)
  private Integer numberOfLesson;

  @Column(name = "moduleNumber", nullable = false)
  private Integer moduleNumber;

  // Construtor para dados básicos
  public Lessons(
      String title,
      String videoLink,
      String description,
      String materials,
      String duration,
      int moduleNumber,
      int numberOfLesson) {
    this.title = title;
    this.videoLink = videoLink;
    this.description = description;
    this.materials = materials;
    this.duration = duration;
    this.moduleNumber = moduleNumber;
    this.numberOfLesson = numberOfLesson;
  }

  // Método helper melhorado para relacionamento bidirecional
  public void setModule(Module module) {
    if (this.module != module) {
      Module oldModule = this.module;
      this.module = module;
      if (oldModule != null && oldModule.getLessons().contains(this)) {
        oldModule.getLessons().remove(this);
      }
      if (module != null && !module.getLessons().contains(this)) {
        module.getLessons().add(this);
      }
    }
  }
}
