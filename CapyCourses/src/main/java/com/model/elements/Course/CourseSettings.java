package com.model.elements.Course;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CourseSettings")
public class CourseSettings {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  private LocalDate dateStart;
  private String durationTotal;
  private boolean isCertificate;
  private boolean isGradeMiniun;
  private boolean visibility;
  private boolean isDateEnd;

  @OneToOne(mappedBy = "courseSettings")
  private Course course;

  public CourseSettings() {
    this.isDateEnd = false;
  }

  public CourseSettings(
      String title,
      LocalDate dateStart,
      String durationTotal,
      boolean isCertificate,
      boolean isGradeMiniun,
      boolean visibility) {
    this.title = title;
    this.dateStart = dateStart;
    this.durationTotal = durationTotal;
    this.isCertificate = isCertificate;
    this.isGradeMiniun = isGradeMiniun;
    this.visibility = visibility;
    this.isDateEnd = false;
  }

  public void setCourse(Course course) {
    if (this.course != course) {
      Course oldCourse = this.course;
      this.course = course;
      if (oldCourse != null && oldCourse.getCourseSettings() == this) {
        oldCourse.setCourseSettings(null);
      }
      if (course != null && course.getCourseSettings() != this) {
        course.setCourseSettings(this);
      }
    }
  }

  public Course getCourse() {
    return course;
  }
}
