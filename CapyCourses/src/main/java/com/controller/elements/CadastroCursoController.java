package com.controller.elements;

import com.dto.AulaDTO;
import com.dto.CadastroCursoDTO;
import com.dto.ModuloDTO;
import com.dto.QuestaoDTO;
import com.dto.QuestionarioDTO;
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

  public String cadastrarCurso(
      String title,
      String description,
      String category,
      String level,
      String tags,
      List<Map<String, Object>> modulesData,
      LocalDate dateStart,
      String durationTotal,
      boolean isCertificate,
      boolean isGradeMiniun,
      Object visibility) {

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
      dto.setDuracaoTotal(durationTotal);
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

    try {
      dto.setNumeroModulo((Integer) moduleData.get("moduleNumber"));
      dto.setTitulo((String) moduleData.get("moduleTitle"));
      dto.setDuracao((String) moduleData.get("moduleDuration"));
      dto.setDescricao((String) moduleData.get("moduleDescription"));

      // Debug
      System.out.println("Dados do módulo recebidos: " + moduleData);

      // Mapeia aulas
      Object lessonsObj = moduleData.get("contentLesson");
      List<AulaDTO> aulas = new ArrayList<>();

      if (lessonsObj instanceof List<?>) {
        List<?> lessonsList = (List<?>) lessonsObj;

        for (Object lessonObj : lessonsList) {
          if (lessonObj instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> lessonData = (Map<String, Object>) lessonObj;

            AulaDTO aulaDTO = new AulaDTO();
            aulaDTO.setModuleNumber(dto.getNumeroModulo());
            aulaDTO.setNumeroAula((Integer) lessonData.get("lessonNumber"));
            aulaDTO.setTitulo((String) lessonData.get("lessonTitle"));
            aulaDTO.setDescricao((String) lessonData.get("lessonDetails"));
            aulaDTO.setMateriais((String) lessonData.get("lessonMaterials"));
            aulaDTO.setVideoLink((String) lessonData.get("lessonVideoLink"));
            aulaDTO.setDuracao((String) lessonData.get("lessonDuration"));

            aulas.add(aulaDTO);
          }
        }
      }
      dto.setAulas(aulas);

      // Mapeia questionários
      Object questionairesObj = moduleData.get("contentQuestionaire");
      List<QuestionarioDTO> questionarios = new ArrayList<>();

      if (questionairesObj instanceof List<?>) {
        List<?> questionairesList = (List<?>) questionairesObj;

        for (Object questionaireObj : questionairesList) {
          if (questionaireObj instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> questionaireData = (Map<String, Object>) questionaireObj;

            QuestionarioDTO questionarioDTO = new QuestionarioDTO();
            questionarioDTO.setTitulo((String) questionaireData.get("questionaireTitle"));
            questionarioDTO.setDescricao((String) questionaireData.get("questionaireDescription"));

            try {
              String scoreStr = (String) questionaireData.get("questionaireScore");
              double score = Double.parseDouble(scoreStr);
              questionarioDTO.setNotaMinima(score);
            } catch (Exception e) {
              System.err.println("Erro ao converter nota: " + e.getMessage());
              questionarioDTO.setNotaMinima(0.0);
            }

            // Processa questões
            Object questionsObj = questionaireData.get("questions");
            if (questionsObj instanceof List) {
              @SuppressWarnings("unchecked")
              List<Map<String, Object>> questionsList = (List<Map<String, Object>>) questionsObj;
              List<QuestaoDTO> questoes = new ArrayList<>();

              for (Map<String, Object> questionData : questionsList) {
                QuestaoDTO questaoDTO = new QuestaoDTO();
                questaoDTO.setPergunta((String) questionData.get("questionText"));
                questaoDTO.setScore((String) questionData.get("questionScore"));
                questaoDTO.setTipo((String) questionData.get("type"));

                if ("DISCURSIVE".equals(questionData.get("type"))) {
                  questaoDTO.setRespostaEsperada((String) questionData.get("expectedAnswer"));
                  questaoDTO.setCriteriosAvaliacao((String) questionData.get("evaluationCriteria"));
                } else {
                  // Processa opções para questões de escolha
                  processChoiceOptions(
                      questionData, questaoDTO, "SINGLE_CHOICE".equals(questionData.get("type")));
                }

                questoes.add(questaoDTO);
                System.out.println("Questão processada: " + questaoDTO);
              }

              questionarioDTO.setQuestoes(questoes);
            }

            questionarios.add(questionarioDTO);
          }
        }
      }
      dto.setQuestionarios(questionarios);

      return dto;

    } catch (Exception e) {
      System.err.println("Erro ao mapear dados do módulo: " + e.getMessage());
      e.printStackTrace();
      throw new RuntimeException("Erro ao processar dados do módulo", e);
    }
  }

  private List<QuestaoDTO> processQuestions(Map<String, Object> questionsData) {
    List<QuestaoDTO> questoes = new ArrayList<>();

    try {
      @SuppressWarnings("unchecked")
      List<Map<String, Object>> questionsList = (List<Map<String, Object>>) questionsData;

      if (questionsList != null) {
        for (Map<String, Object> questionData : questionsList) {
          QuestaoDTO questaoDTO = new QuestaoDTO();
          questaoDTO.setPergunta((String) questionData.get("questionText"));
          questaoDTO.setScore((String) questionData.get("questionScore"));
          questaoDTO.setTipo((String) questionData.get("type"));

          if ("DISCURSIVE".equals(questionData.get("type"))) {
            questaoDTO.setRespostaEsperada((String) questionData.get("expectedAnswer"));
            questaoDTO.setCriteriosAvaliacao((String) questionData.get("evaluationCriteria"));
          } else {
            // Processa opções para questões de escolha
            processChoiceOptions(
                questionData, questaoDTO, "SINGLE_CHOICE".equals(questionData.get("type")));
          }

          questoes.add(questaoDTO);
          System.out.println("Questão processada: " + questaoDTO);
        }
      }
    } catch (Exception e) {
      System.err.println("Erro ao processar questões: " + e.getMessage());
      e.printStackTrace();
    }

    return questoes;
  }

  private void processChoiceOptions(
      Map<String, Object> questionData, QuestaoDTO questaoDTO, boolean isSingleChoice) {
    @SuppressWarnings("unchecked")
    List<Map<String, Object>> optionsList = (List<Map<String, Object>>) questionData.get("options");

    if (optionsList != null && !optionsList.isEmpty()) {
      List<String> alternativas = new ArrayList<>();
      List<Integer> correctIndexes = new ArrayList<>();

      for (int i = 0; i < optionsList.size(); i++) {
        Map<String, Object> option = optionsList.get(i);
        String text = (String) option.get("optionText");
        Boolean isSelected = (Boolean) option.get("isSelected");

        if (text != null && !text.trim().isEmpty()) {
          alternativas.add(text);
          if (Boolean.TRUE.equals(isSelected)) {
            if (isSingleChoice) {
              questaoDTO.setAlternativaCorreta(i);
            } else {
              correctIndexes.add(i);
            }
          }
        }
      }

      questaoDTO.setAlternativas(alternativas);
      if (!isSingleChoice) {
        questaoDTO.setAlternativasCorretas(correctIndexes);
      }
      questaoDTO.setOptions(optionsList);
    }
  }
}
