package com.model.Course;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class CourseSettings {
    String title;
    LocalDate dateStart;
    LocalDate dateEnd;
    String durationTotal;
    boolean isDateEnd;
    boolean isCertificate;
    boolean isGradeMiniun;
    Object ComboBoxVisibily;
}
