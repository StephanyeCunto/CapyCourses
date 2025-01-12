package com.model.elements.Course;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import lombok.NoArgsConstructor;

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
    private LocalDate dateEnd;
    private String durationTotal;
    private boolean isDateEnd;
    private boolean isCertificate;
    private boolean isGradeMiniun;
    private boolean visibility;

    @OneToOne(mappedBy = "courseSettings")
    private Course course;

    public CourseSettings() {}

    public CourseSettings(String title, LocalDate dateStart, LocalDate dateEnd, 
            String durationTotal, boolean isCertificate, 
            boolean isDateEnd, boolean isGradeMiniun, boolean visibility) {
        this.title = title;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.durationTotal = durationTotal;
        this.isCertificate = isCertificate;
        this.isDateEnd = isDateEnd;
        this.isGradeMiniun = isGradeMiniun;
        this.visibility = visibility;
    }

    public boolean isDateEnd() {
        return isDateEnd;
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
