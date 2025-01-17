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
import com.util.JPAUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

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
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = null;
        
        try {
            tx = em.getTransaction();
            tx.begin();
            
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
            
            // Persistir o curso primeiro
            em.persist(course);
            em.flush();

            // Processar módulos
            if (dto.getModulos() != null) {
                for (ModuloDTO moduloDTO : dto.getModulos()) {
                    Module module = createModuleFromDTO(moduloDTO);
                    module.setCourse(course);
                    em.persist(module);
                    em.flush();
                    
                    // Processar aulas do módulo
                    processLessons(moduloDTO, module, em);
                    
                    // Processar questionários do módulo
                    processQuestionairesAndQuestions(moduloDTO, module, em);
                }
            }

            tx.commit();
            return course;
            
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Erro ao criar curso: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
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

    private void processLessons(ModuloDTO moduloDTO, Module module, EntityManager em) {
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

    private void processQuestionairesAndQuestions(ModuloDTO moduloDTO, Module module, EntityManager em) {
        if (moduloDTO.getQuestionarios() != null) {
            System.out.println("Módulo tem " + moduloDTO.getQuestionarios().size() + " questionários");
            for (QuestionarioDTO questionarioDTO : moduloDTO.getQuestionarios()) {
                System.out.println("Detalhes do questionário: " + questionarioDTO.toString());
                
                Questionaire questionaire = new Questionaire();
                questionaire.setModule(module);
                questionaire.setTitle(questionarioDTO.getTitulo());
                questionaire.setDescription(questionarioDTO.getDescricao());
                questionaire.setNumber(String.valueOf(module.getModuleNumber()));
                questionaire.setScore(String.valueOf(questionarioDTO.getNotaMinima()));
                
                em.persist(questionaire);
                em.flush();
                
                System.out.println("Questionário persistido com ID: " + questionaire.getId());
                
                if (questionarioDTO.getQuestoes() != null) {
                    System.out.println("Questionário tem " + questionarioDTO.getQuestoes().size() + " questões");
                    
                    for (QuestaoDTO questaoDTO : questionarioDTO.getQuestoes()) {
                        System.out.println("Processando questão: " + questaoDTO.getPergunta());
                        
                        try {
                            Question question = new Question();
                            question.setQuestionaire(questionaire);
                            question.setText(questaoDTO.getPergunta());
                            question.setType("SINGLE_CHOICE");
                            question.setScore(questaoDTO.getScore());
                            
                            // Processar opções
                            if (questaoDTO.getOptions() != null && !questaoDTO.getOptions().isEmpty()) {
                                List<String> alternativas = new ArrayList<>();
                                String respostaCorreta = null;
                                
                                System.out.println("Processando " + questaoDTO.getOptions().size() + " opções");
                                
                                for (Map<String, Object> option : questaoDTO.getOptions()) {
                                    String texto = (String) option.get("optionText");
                                    boolean selecionada = (boolean) option.get("isSelected");
                                    
                                    alternativas.add(texto);
                                    if (selecionada) {
                                        respostaCorreta = texto;
                                    }
                                    
                                    System.out.println("Opção: " + texto + " (Selecionada: " + selecionada + ")");
                                }
                                
                                question.setAnswers(String.join("|", alternativas));
                                question.setCorrectAnswers(respostaCorreta);
                                
                                System.out.println("Tentando persistir questão...");
                                em.persist(question);
                                em.flush();
                                System.out.println("Questão persistida com ID: " + question.getId());
                                
                            } else {
                                System.out.println("Nenhuma opção encontrada para a questão");
                            }
                        } catch (Exception e) {
                            System.err.println("Erro ao processar questão: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                } else {
                    System.out.println("Nenhuma questão encontrada no questionário");
                }
            }
        } else {
            System.out.println("Nenhum questionário encontrado no módulo");
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

    // Método auxiliar para mapear os dados do questionário
    private List<QuestaoDTO> mapQuestionsFromData(List<Map<String, Object>> questionsData) {
        List<QuestaoDTO> questoes = new ArrayList<>();
        
        for (Map<String, Object> questionData : questionsData) {
            QuestaoDTO questaoDTO = new QuestaoDTO();
            questaoDTO.setPergunta((String) questionData.get("questionText"));
            
            @SuppressWarnings("unchecked")
            List<String> alternativas = (List<String>) questionData.get("responses");
            if (alternativas != null) {
                questaoDTO.setAlternativas(alternativas);
            }
            
            questoes.add(questaoDTO);
        }
        
        return questoes;
    }
}
