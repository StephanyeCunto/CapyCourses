package com.model.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "forums")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Forum {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String author;
  private String title;

  @Column(columnDefinition = "TEXT")
  private String description;

  private String category;
  private LocalDateTime dateTime;
  private int viewCount;
  private int likeCount;
  private int commentsCount;

  @Column(columnDefinition = "TEXT")
  private String question;

  @OneToMany(mappedBy = "forum", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ForumComment> comments = new ArrayList<>();
}
