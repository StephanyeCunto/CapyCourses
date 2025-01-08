package com.util;

import java.util.List;

import com.dao.CourseDAO;
import com.dao.ModuleDAO;
import com.dao.LessonsDAO;
import com.model.elements.Course.Course;
import com.model.elements.Course.CourseReader;
import com.model.elements.Course.Module;
import com.model.elements.Course.Lessons;



public class DataMigration {
    public static void migrateAll() {
        CourseReader reader = new CourseReader();
        CourseDAO courseDAO = new CourseDAO();
        ModuleDAO moduleDAO = new ModuleDAO();
        LessonsDAO lessonsDAO = new LessonsDAO();
        
        // Migrar cursos
        List<Course> courses = reader.readCourses();
        for (Course course : courses) {
            courseDAO.save(course);
            
            // Migrar módulos de cada curso
            List<Module> modules = reader.courseModule(course.getTitle());
            for (Module module : modules) {
                module.setCourse(course);
                moduleDAO.save(module);
                
                // Migrar aulas de cada módulo
                for (Lessons lesson : module.getLessons()) {
                    lesson.setModule(module);
                    lessonsDAO.save(lesson);
                }
            }
        }
    }
} 