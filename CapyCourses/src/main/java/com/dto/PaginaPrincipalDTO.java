package com.dto;

import java.time.LocalDate;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class PaginaPrincipalDTO {
     private String name;
    private String title;
    private String description;
    private String categoria;
    private String nivel;
    private double rating;
    private LocalDate dateEnd;
    private String durationTotal;
    private boolean isDateEnd;
    private boolean isCertificate;
    private String status;
    private int percentual;

    public void loadCourses(String name, String title, String description, String categoria, String nivel, double rating) {
        this.name = name;
        this.title = title;
        this.description = description;
        this.categoria = categoria;
        this.nivel = nivel;
        this.rating = rating;
    }

    public void loadCoursesSettings(LocalDate dateEnd, String durationTotal, boolean isDateEnd, boolean isCertificate, String status, int percentual) {
        this.dateEnd = dateEnd;
        this.durationTotal = durationTotal;
        this.isDateEnd = isDateEnd;
        this.isCertificate = isCertificate;
        this.status = status;
        this.percentual = percentual;
    }

    public boolean isDateEnd() {
        return isDateEnd;
    }
}
