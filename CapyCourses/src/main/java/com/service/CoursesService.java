package com.service;

import com.dao.*;
import com.dto.*;
import com.model.elements.Course.Course;
import com.model.elements.Course.Module;
import com.model.elements.Course.Lessons;
import com.model.elements.Course.Questionaire;
import com.model.elements.Course.Question;
import com.model.elements.Course.CourseSettings;
import com.singleton.UserSession;
import java.util.ArrayList;
import java.util.List;

public class CoursesService {
    private final CourseDAO courseDAO;
    private final ModuleDAO moduleDAO;
    private final LessonsDAO lessonsDAO;
    private final QuestionaireDAO questionaireDAO;
    private final QuestionsDAO questionsDAO;

    public CoursesService() {
        this.courseDAO = new CourseDAO();
        this.moduleDAO = new ModuleDAO();
        this.lessonsDAO = new LessonsDAO();
        this.questionaireDAO = new QuestionaireDAO();
        this.questionsDAO = new QuestionsDAO();
    }

    public Course createCourse(CadastroCursoDTO dto) {
        try {
            // Criar e configurar o curso
            Course course = new Course();
            course.setTitle(dto.getTitulo());
            course.setDescription(dto.getDescricao());
            course.setCategoria(dto.getCategoria());
            course.setNivel(dto.getNivel());
            course.setName(UserSession.getInstance().getUserEmail());
            course.setRating(0.0);

            // Criar e configurar CourseSettings
            CourseSettings settings = new CourseSettings(
                dto.getTitulo(),
                dto.getDataInicio(),
                dto.getDataFim(),
                dto.getDuracaoTotal(),
                dto.isEmitirCertificado(),
                dto.isSemDataFinal(),
                dto.isExigirNotaMinima(),
                "public".equalsIgnoreCase(dto.getVisibilidade())
            );
            
            // Estabelecer relacionamento bidirecional
            course.setCourseSettings(settings);
            settings.setCourse(course);

            // Processar módulos
            if (dto.getModulos() != null) {
                for (ModuloDTO moduloDTO : dto.getModulos()) {
                    Module module = createModuleFromDTO(moduloDTO);
                    course.addModule(module);
                    
                    // Processar aulas do módulo
                    processLessons(moduloDTO, module);
                    
                    // Processar questionários do módulo
                    processQuestionaires(moduloDTO, module);
                }
            }

            // Salvar o curso (cascade deve propagar para todas as entidades relacionadas)
            courseDAO.save(course);
            
            return course;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar curso: " + e.getMessage(), e);
        }
    }

    private Module createModuleFromDTO(ModuloDTO dto) {
        Module module = new Module();
        module.setTitle(dto.getTitulo());
        module.setDescription(dto.getDescricao());
        module.setDuration(dto.getDuracao());
        module.setModuleNumber(dto.getNumeroModulo());
        return module;
    }

    private void processLessons(ModuloDTO moduloDTO, Module module) {
        if (moduloDTO.getAulas() != null) {
            for (AulaDTO aulaDTO : moduloDTO.getAulas()) {
                Lessons lesson = new Lessons(
                    aulaDTO.getTitulo(),
                    aulaDTO.getVideoLink(),
                    aulaDTO.getDescricao(),
                    aulaDTO.getMateriais(),
                    aulaDTO.getDuracao(),
                    module.getModuleNumber(),
                    aulaDTO.getNumeroAula()
                );
                module.addLesson(lesson);
            }
        }
    }

    private void processQuestionaires(ModuloDTO moduloDTO, Module module) {
        if (moduloDTO.getQuestionarios() != null) {
            for (QuestionarioDTO questionarioDTO : moduloDTO.getQuestionarios()) {
                Questionaire questionaire = new Questionaire();
                questionaire.setTitle(questionarioDTO.getTitulo());
                questionaire.setDescription(questionarioDTO.getDescricao());
                questionaire.setNumber(String.valueOf(module.getModuleNumber()));
                questionaire.setScore(String.valueOf(questionarioDTO.getNotaMinima()));
                module.addQuestionaire(questionaire);

                // Processar questões
                processQuestions(questionarioDTO, questionaire);
            }
        }
    }

    private void processQuestions(QuestionarioDTO questionarioDTO, Questionaire questionaire) {
        if (questionarioDTO.getQuestoes() != null) {
            for (QuestaoDTO questaoDTO : questionarioDTO.getQuestoes()) {
                Question question = new Question();
                question.setText(questaoDTO.getPergunta());
                question.setType("multiple_choice"); // Pode ser parametrizado se necessário
                question.setAnswers(String.join(";", questaoDTO.getAlternativas()));
                question.setCorrectAnswers(String.valueOf(questaoDTO.getAlternativaCorreta()));
                question.setNumber(String.valueOf(questionaire.getQuestions().size() + 1));
                question.setScore("1.0"); // Pode ser parametrizado se necessário
                question.setArea("general"); // Pode ser parametrizado se necessário
                
                questionaire.addQuestion(question);
            }
        }
    }

    // Métodos auxiliares para busca
    public List<Course> findAllCourses() {
        return courseDAO.findAll();
    }

    public Course findCourseById(Long id) {
        return courseDAO.findById(id);
    }

    public Course findCourseByTitle(String title) {
        return courseDAO.findByTitle(title);
    }


    private Lessons createLesson(AulaDTO dto, Module module) {
        Lessons lesson = new Lessons();
        lesson.setTitle(dto.getTitulo());
        lesson.setDescription(dto.getDescricao());
        lesson.setDuration(dto.getDuracao());
        lesson.setNumberOfLesson(dto.getNumeroAula());
        lesson.setModuleNumber(module.getModuleNumber());
        lesson.setModule(module);
        return lesson;
    }

    private Questionaire createQuestionaire(QuestionarioDTO dto, Module module) {
        Questionaire questionaire = new Questionaire();
        questionaire.setTitle(dto.getTitulo());
        questionaire.setModule(module);
        // Adicionar outros campos conforme necessário
        return questionaire;
    }

    private Question createQuestion(QuestaoDTO dto) {
        Question question = new Question();
        question.setText(dto.getPergunta());
        // Converter lista de alternativas para o formato esperado
        question.setAnswers(String.join(";", dto.getAlternativas()));
        question.setCorrectAnswers(String.valueOf(dto.getAlternativaCorreta()));
        return question;
    }
}
