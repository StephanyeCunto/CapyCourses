package com.model.elements.Course;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dao.CourseDAO;
import com.singleton.UserSession;

public class CourseReader {
    private CourseDAO courseDAO = new CourseDAO();

    public List<Course> readCourses() {
        return courseDAO.findAll();
    }

    public CourseSettings courseSettings(String titleVerific) {

        try (BufferedReader br = new BufferedReader(
                new FileReader("capycourses/src/main/resources/com/bd/bd_settingsCurso.csv"))) {
            String title;
            LocalDate dateStart;
            LocalDate dateEnd;
            String durationTotal;
            boolean isDateEnd;
            boolean isCertificate;
            boolean isGradeMiniun;
            Object ComboBoxVisibily;
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                title = values[0];
                if (titleVerific.equals(title)) {
                    dateStart = LocalDate.parse(values[1]);
                    dateEnd = LocalDate.parse(values[2]);
                    durationTotal = values[3];
                    isDateEnd = Boolean.parseBoolean(values[4]);
                    isCertificate = Boolean.parseBoolean(values[5]);
                    isGradeMiniun = Boolean.parseBoolean(values[6]);
                    ComboBoxVisibily = values[7];

                    boolean visibility = "public".equalsIgnoreCase(values[7]) || "true".equalsIgnoreCase(values[7]);

                    CourseSettings courseSetting = new CourseSettings(title, dateStart, dateEnd, durationTotal,
                            isDateEnd,
                            isCertificate, isGradeMiniun, visibility);
                    return courseSetting;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Module> courseModule(String titleVerific) {
        List<Module> modules = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new FileReader("capycourses/src/main/resources/com/bd/bd_modulos.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String titleCourse = values[0];
                if (titleVerific.equals(titleCourse)) {
                    int moduleNumber = Integer.parseInt(values[1]);
                    String title = values[2];
                    String duration = values[3];
                    String description = values[4];

                    List<Lessons> lessons = courseLessons(titleCourse, moduleNumber);
                   // Module moduleCourse = new Module(moduleNumber, title, description, duration, lessons);
                    // modules.add(moduleCourse);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return modules;
    }

    public List<Lessons> courseLessons(String titleCourseVerific, int moduleNumberVerific) {
        List<Lessons> lessons = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new FileReader("capycourses/src/main/resources/com/bd/bd_aulas.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String titleCourse = values[0];
                int moduleNumber = Integer.parseInt(values[2]);

                if (titleCourseVerific.equals(titleCourse) && moduleNumberVerific == moduleNumber) {
                    String module = values[1];
                    int numberOfLesson = Integer.parseInt(values[3]);
                    String title = values[4];
                    String videoLink = values[5];
                    String details = values[6];
                    String materials = values[7];
                    String duration = values[8];

                //   Lessons lesson = new Lessons(title, videoLink, details, materials, duration, module,
                 //           moduleNumber, numberOfLesson);
                 //   lessons.add(lesson);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lessons;
    }

    public List<Questionaire> courseQuestionaire(String titleCourseVerific, int moduleNumberVerific) {
        List<Questionaire> questionaires = new ArrayList<>();

        return questionaires;
    }

    public Course course(String titleVerific) {
        List<Course> courses = courseDAO.findAll();
        return courses.stream()
                .filter(c -> c.getTitle().equals(titleVerific))
                .findFirst()
                .orElse(null);
    }

    public int percentual(String title) {
        int percentual = 0;
        String email = UserSession.getInstance().getUserEmail();
        try (BufferedReader br = new BufferedReader(
                new FileReader("capycourses/src/main/resources/com/bd/bd_myCourse.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if(email==null || title == null){
                    return 0;
                }
                if (email.equals(values[0]) && title.equals(values[1])) {
                    percentual = Integer.parseInt(values[3]);
                    return percentual;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return percentual;
    }

    public String status(String title) {
        String status = "";
        String email = UserSession.getInstance().getUserEmail();
        try (BufferedReader br = new BufferedReader(
                new FileReader("capycourses/src/main/resources/com/bd/bd_myCourse.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (email.equals(values[0]) && title.equals(values[1])) {
                    status = values[2];
                    return status;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }

    public static Questionaire questionaireSettings(Map<String, Object> questionaireData) {
        Questionaire questionaire = new Questionaire();
        questionaire.setTitle((String) questionaireData.get("questionaireTitle0"));
        questionaire.setNumber((String) questionaireData.get("questionaireNumber0"));
        questionaire.setScore((String) questionaireData.get("questionaireScore0"));
        questionaire.setDescription((String) questionaireData.get("questionaireDescription0"));
        
        return questionaire;
    }
}
