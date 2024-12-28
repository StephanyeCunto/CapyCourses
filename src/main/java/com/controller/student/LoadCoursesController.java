package com.controller.student;

import java.util.ArrayList;
import java.util.List;

import com.dto.paginaPrincipalDto;
import com.model.Course.Course;
import com.model.Course.CourseReader;
import com.model.Course.CourseSettings;
import com.model.student.MyCourse;
import com.model.student.Student;
import com.singleton.UserSession;

public class LoadCoursesController {
    private List<MyCourse> course;

    CourseReader reader = new CourseReader();
    private List<Course> coursesTotais = reader.readCourses();
    private List<paginaPrincipalDto> courses = new ArrayList<>();
    
    public List<paginaPrincipalDto> loadCourses() {
        Student student = new Student(UserSession.getInstance().getUserEmail());
        course = student.getCourse();
        for (int i = 0; i < coursesTotais.size(); i++) {
            if (checkCourses(coursesTotais.get(i))) {
                paginaPrincipalDto dto = new paginaPrincipalDto();
                dto.loadCourses(coursesTotais.get(i).getName(), coursesTotais.get(i).getTitle(), coursesTotais.get(i).getDescription(),
                        coursesTotais.get(i).getCategoria(), coursesTotais.get(i).getNivel(), coursesTotais.get(i).getRating());

                CourseReader courseReader = new CourseReader();
                CourseSettings courseSettings = courseReader.courseSettings(coursesTotais.get(i).getTitle());
                dto.loadCoursesSettings(courseSettings.getDateEnd(),
                        courseSettings.getDurationTotal(), courseSettings.isDateEnd(),
                        courseSettings.isCertificate(), "notStarted", 0);
                courses.add(dto);
            }
        }
        return courses;
    }

    private boolean checkCourses(Course courseCheck) {
        for (int i = 0; i < course.size(); i++) {
            MyCourse courseT = course.get(i);
            Course course = courseT.getCourse();
            if (courseCheck.getTitle().equals(course.getTitle())) {
                return false;
            }
        }
        return true;
    }
}
