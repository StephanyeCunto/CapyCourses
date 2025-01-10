package com.controller.elements;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import com.dao.*;
import com.model.elements.Course.*;
import com.singleton.UserSession;

public class CadastroCursoController {
    private final CourseDAO courseDAO;
    private final ModuleDAO moduleDAO;
    private final LessonsDAO lessonsDAO;
    private final QuestionaireDAO questionaireDAO;
    private final QuestionsDAO questionsDAO;
    private final CourseSettingsDAO courseSettingsDAO;
    
    public CadastroCursoController() {
        this.courseDAO = new CourseDAO();
        this.moduleDAO = new ModuleDAO();
        this.lessonsDAO = new LessonsDAO();
        this.questionaireDAO = new QuestionaireDAO();
        this.questionsDAO = new QuestionsDAO();
        this.courseSettingsDAO = new CourseSettingsDAO();
    }
    
    @SuppressWarnings("unchecked")
    public String cadastrarCurso(String title, String description, String category, String level, 
            String tags, List<Map<String, Object>> modulesData, LocalDate dateStart, 
            LocalDate dateEnd, String durationTotal, boolean isDateEnd, 
            boolean isCertificate, boolean isGradeMiniun, Object visibility) {
            
        try {
            // Cria o curso principal
            Course course = new Course();
            course.setTitle(title);
            course.setDescription(description);
            course.setCategoria(category);
            course.setNivel(level);
            course.setName(UserSession.getInstance().getUserEmail());
            course.setRating(0.0);
            
            courseDAO.save(course);

            // Processa cada módulo
            for (Map<String, Object> moduleData : modulesData) {
                com.model.elements.Course.Module module = new com.model.elements.Course.Module();
                module.setTitle((String) moduleData.get("moduleTitle"));
                module.setModuleNumber((Integer) moduleData.get("moduleNumber"));
                module.setDuration((String) moduleData.get("moduleDuration"));
                module.setDescription((String) moduleData.get("moduleDescription"));
                module.setCourse(course);
                
                moduleDAO.save(module);

                // Processa questionários do módulo
                List<Map<String, Object>> questionaires = 
                    (List<Map<String, Object>>) moduleData.get("contentQuestionaire");
                
                if (questionaires != null) {
                    for (Map<String, Object> questionaireData : questionaires) {
                        Questionaire questionaire = new Questionaire();
                        questionaire.setTitle((String) questionaireData.get("questionaireTitle"));
                        questionaire.setNumber((String) questionaireData.get("questionaireNumber"));
                        questionaire.setScore((String) questionaireData.get("questionaireScore"));
                        questionaire.setDescription((String) questionaireData.get("questionaireDescription"));
                        questionaire.setModule(module);
                        
                        questionaireDAO.save(questionaire);

                        // Processa questões do questionário
                        Map<String, Object> questions = 
                            (Map<String, Object>) questionaireData.get("questions");
                        
                        if (questions != null) {
                            for (int i = 0; questions.get("questionNumber" + i) != null; i++) {
                                Question question = new Question();
                                question.setNumber((String) questions.get("questionNumber" + i));
                                question.setScore((String) questions.get("questionScore" + i));
                                question.setText((String) questions.get("questionText" + i));
                                question.setType((String) questions.get("type" + i));
                                question.setQuestionaire(questionaire);
                                
                                Map<String, Object> responses = 
                                    (Map<String, Object>) questions.get("response" + i);
                                
                                StringBuilder answers = new StringBuilder();
                                StringBuilder isTrues = new StringBuilder();
                                
                                if (question.getType().equals("SIMGLE_CHOICE") || 
                                    question.getType().equals("MULTIPLE_CHOICE")) {
                                    for (int j = 0; responses.get("responseField" + j) != null; j++) {
                                        answers.append(responses.get("responseField" + j)).append(";");
                                        isTrues.append(responses.get("responseIsTrue" + j)).append(";");
                                    }
                                } else {
                                    answers.append(responses.get("responseField0"));
                                    question.setArea((String) responses.get("responseField20"));
                                }
                                
                                question.setAnswers(answers.toString());
                                question.setCorrectAnswers(isTrues.toString());
                                
                                questionsDAO.save(question);
                            }
                        }
                    }
                }

                // Processa aulas do módulo
                List<Map<String, Object>> lessons = 
                    (List<Map<String, Object>>) moduleData.get("contentLesson");
                
                if (lessons != null) {
                    for (Map<String, Object> lessonData : lessons) {
                        Lessons lesson = new Lessons();
                        lesson.setTitle((String) lessonData.get("lessonTitle"));
                        lesson.setVideoLink((String) lessonData.get("lessonVideoLink"));
                        lesson.setDescription((String) lessonData.get("lessonDetails"));
                        lesson.setMaterials((String) lessonData.get("lessonMaterials"));
                        lesson.setDuration((String) lessonData.get("lessonDuration"));
                        lesson.setModule(module);
                        lesson.setModuleNumber(module.getModuleNumber());
                        
                        // Garante que numberOfLesson não será null
                        Integer lessonNumber = (Integer) lessonData.get("lessonNumber");
                        if (lessonNumber == null) {
                            lessonNumber = 1; // ou outro valor padrão
                        }
                        lesson.setNumberOfLesson(lessonNumber);
                        
                        lessonsDAO.save(lesson);
                    }
                }
            }

            // Salva as configurações do curso
            CourseSettings settings = new CourseSettings(
                title, dateStart, dateEnd, durationTotal,
                isDateEnd, isCertificate, isGradeMiniun, 
                visibility.toString()
            );
            
            // Associa as configurações ao curso existente
            course.setCourseSettings(settings);
            courseDAO.save(course);
            
            settings.setCourse(course);
            courseSettingsDAO.save(settings);
            
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
} 