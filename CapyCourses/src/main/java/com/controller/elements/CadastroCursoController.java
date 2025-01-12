package com.controller.elements;

import com.dto.CadastroCursoDTO;
import com.dto.ModuloDTO;
import com.dto.AulaDTO;
import com.dto.QuestionarioDTO;
import com.dto.QuestaoDTO;
import com.service.CoursesService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CadastroCursoController {
    private final CoursesService coursesService;
    
    public CadastroCursoController() {
        this.coursesService = new CoursesService();
    }
    
    public String cadastrarCurso(String title, String description, String category, 
            String level, String tags, List<Map<String, Object>> modulesData,
            LocalDate dateStart, LocalDate dateEnd, String durationTotal,
            boolean isDateEnd, boolean isCertificate, boolean isGradeMiniun, Object visibility) {
        
        try {
            CadastroCursoDTO dto = new CadastroCursoDTO();
            
            // Dados básicos
            dto.setTitulo(title);
            dto.setDescricao(description);
            dto.setCategoria(category);
            dto.setNivel(level);
            dto.setTags(Arrays.asList(tags.split(",")));
            
            // Configurações
            dto.setDataInicio(dateStart);
            dto.setDataFim(dateEnd);
            dto.setDuracaoTotal(durationTotal);
            dto.setSemDataFinal(isDateEnd);
            dto.setEmitirCertificado(isCertificate);
            dto.setExigirNotaMinima(isGradeMiniun);
            dto.setVisibilidade(visibility.toString());
            
            // Módulos
            List<ModuloDTO> modulos = new ArrayList<>();
            for (Map<String, Object> moduleData : modulesData) {
                ModuloDTO moduloDTO = mapModuleData(moduleData);
                modulos.add(moduloDTO);
            }
            dto.setModulos(modulos);
            
            // Chama o service
            coursesService.createCourse(dto);
            return "success";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    private ModuloDTO mapModuleData(Map<String, Object> moduleData) {
        ModuloDTO dto = new ModuloDTO();
        
        dto.setNumeroModulo((Integer) moduleData.get("moduleNumber"));
        dto.setTitulo((String) moduleData.get("moduleTitle"));
        dto.setDuracao((String) moduleData.get("moduleDuration"));
        dto.setDescricao((String) moduleData.get("moduleDescription"));
        
        // Mapeia aulas
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> lessonsData = (List<Map<String, Object>>) moduleData.get("contentLesson");
        if (lessonsData != null) {
            dto.setAulas(mapLessonsData(lessonsData));
        }
        
        // Mapeia questionários
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> questionairesData = (List<Map<String, Object>>) moduleData.get("contentQuestionaire");
        if (questionairesData != null) {
            dto.setQuestionarios(mapQuestionairesData(questionairesData));
        }
        
        return dto;
    }

    private List<AulaDTO> mapLessonsData(List<Map<String, Object>> lessonsData) {
        List<AulaDTO> aulas = new ArrayList<>();
        
        for (Map<String, Object> lessonData : lessonsData) {
            AulaDTO aulaDTO = new AulaDTO();
            
            aulaDTO.setModuleNumber((Integer) lessonData.get("moduleNumber"));
            aulaDTO.setNumeroAula((Integer) lessonData.get("lessonNumber0"));
            aulaDTO.setTitulo((String) lessonData.get("lessonTitle0"));
            aulaDTO.setDescricao((String) lessonData.get("lessonDetails0"));
            aulaDTO.setMateriais((String) lessonData.get("lessonMaterials0"));
            aulaDTO.setVideoLink((String) lessonData.get("lessonVideoLink0"));
            aulaDTO.setDuracao((String) lessonData.get("lessonDuration0"));
            
            aulas.add(aulaDTO);
        }
        
        return aulas;
    }

    private List<QuestionarioDTO> mapQuestionairesData(List<Map<String, Object>> questionairesData) {
        List<QuestionarioDTO> questionarios = new ArrayList<>();
        
        for (Map<String, Object> questionaireData : questionairesData) {
            QuestionarioDTO questionarioDTO = new QuestionarioDTO();
            
            questionarioDTO.setTitulo((String) questionaireData.get("questionaireTitle0"));
            questionarioDTO.setDescricao((String) questionaireData.get("questionaireDescription0"));
            questionarioDTO.setNotaMinima(Double.parseDouble(questionaireData.get("questionaireScore0").toString()));
            
            @SuppressWarnings("unchecked")
            Map<String, Object> questionsData = (Map<String, Object>) questionaireData.get("questions0");
            if (questionsData != null) {
                questionarioDTO.setQuestoes(mapQuestionsData(questionsData));
            }
            
            questionarios.add(questionarioDTO);
        }
        
        return questionarios;
    }

    private List<QuestaoDTO> mapQuestionsData(Map<String, Object> questionsData) {
        List<QuestaoDTO> questoes = new ArrayList<>();
        
        QuestaoDTO questaoDTO = new QuestaoDTO();
        questaoDTO.setPergunta((String) questionsData.get("questionText0"));
        
        // Processa as alternativas
        @SuppressWarnings("unchecked")
        Map<String, Object> responseData = (Map<String, Object>) questionsData.get("response0");
        if (responseData != null) {
            List<String> alternativas = new ArrayList<>();
            int alternativaCorreta = -1;
            
            // Processa cada resposta
            for (int i = 0; responseData.containsKey("responseField" + i); i++) {
                String alternativa = (String) responseData.get("responseField" + i);
                alternativas.add(alternativa);
                
                // Verifica se é a alternativa correta
                Boolean isCorrect = (Boolean) responseData.get("responseIsTrue" + i);
                if (isCorrect != null && isCorrect) {
                    alternativaCorreta = i;
                }
            }
            
            questaoDTO.setAlternativas(alternativas);
            questaoDTO.setAlternativaCorreta(alternativaCorreta);
        }
        
        questoes.add(questaoDTO);
        return questoes;
    }
} 