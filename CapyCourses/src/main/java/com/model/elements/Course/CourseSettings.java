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

@Getter
@Setter
@AllArgsConstructor
@Entity
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
    private String visibility;

    @OneToOne(mappedBy = "courseSettings")
    private Course course;

    // Construtor vazio necessário para JPA
    public CourseSettings() {}

    // Construtor com parâmetros
    public CourseSettings(String title, LocalDate dateStart, LocalDate dateEnd, 
            String durationTotal, boolean isDateEnd, boolean isCertificate, 
            boolean isGradeMiniun, String visibility) {
        this.title = title;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.durationTotal = durationTotal;
        this.isDateEnd = isDateEnd;
        this.isCertificate = isCertificate;
        this.isGradeMiniun = isGradeMiniun;
        this.visibility = visibility;
    }

    public boolean isDateEnd() {
        return isDateEnd;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }
}
