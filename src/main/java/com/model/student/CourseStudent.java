package com.model.student;

import com.model.Course.Course;

import java.util.List;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class CourseStudent {
    private String emailUser;
    private Course course;
    private List<Integer> completedModules;
    private List<Integer> completedLessons;
    private List<Integer> completedQuizzes;
}
