package com.model.elements.Course;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class CourseSettings {
   private String title;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private String durationTotal;
    private boolean isDateEnd;
    private boolean isCertificate;
    private boolean isGradeMiniun;
    private Object ComboBoxVisibily;

    public boolean isDateEnd() {
        return isDateEnd;
    }
}
