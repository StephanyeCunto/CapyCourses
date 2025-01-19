package com.model.elements;

import com.model.elements.Course.Course;
import com.dao.CourseDAO;
import com.dao.ModuleDAO;
import com.dao.LessonsDAO;
import com.dao.QuestionaireDAO;

import lombok.*;

@Getter
@Setter
public class MyCourse {
    private String email;
    private Course course;
    private String courseTitle;
    private int moduleTotal;
    private int moduleCompleted;
    private int lessonTotal;
    private int lessonCompleted;
    private int questionaireTotal;
    private int questionaireCompleted;
    private int courseTotal;
    private String status;
    private int percentual;
    
    public MyCourse(String title) {
        this.courseTitle = title;
        CourseDAO courseDAO = new CourseDAO();
        this.course = courseDAO.findByTitle(title);
        
        if (this.course != null && this.course.getCourseSettings() != null) {
            ModuleDAO moduleDAO = new ModuleDAO();
            LessonsDAO lessonDAO = new LessonsDAO();
            QuestionaireDAO questionaireDAO = new QuestionaireDAO();
            
            this.moduleTotal = course.getModules().size();
            this.lessonTotal = course.getModules().stream()
                .mapToInt(module -> module.getLessons().size())
                .sum();
            this.questionaireTotal = course.getModules().stream()
                .mapToInt(module -> module.getQuestionaires().size())
                .sum();
            
            this.moduleCompleted = 0;
            this.lessonCompleted = 0;
            this.questionaireCompleted = 0;

            this.courseTotal = this.moduleTotal + this.lessonTotal + this.questionaireTotal;
            // TODO: Atualizar para usar StudentCourseDAO para obter progresso e status
            this.percentual = 0;
            this.status = "started";
        }
    }
}
