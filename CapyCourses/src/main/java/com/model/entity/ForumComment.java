package com.model.entity;

import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "forum_comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForumComment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "forum_id")
  private Forum forum;

  private String userName;

  @Column(columnDefinition = "TEXT")
  private String commentText;

  private LocalDateTime commentDate;
}
