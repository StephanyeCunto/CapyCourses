package com.dto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionarioDTO {
  private String titulo;
  private String descricao;
  private double notaMinima;
  private List<QuestaoDTO> questoes;

  @Override
  public String toString() {
    return "QuestionarioDTO{"
        + "titulo='"
        + titulo
        + '\''
        + ", descricao='"
        + descricao
        + '\''
        + ", notaMinima="
        + notaMinima
        + ", questoes="
        + (questoes != null ? questoes.size() : "null")
        + '}';
  }

  public void setQuestions(List<Map<String, Object>> questionsData) {
    if (questionsData != null) {
      this.questoes =
          questionsData.stream()
              .map(
                  questionData -> {
                    QuestaoDTO questao = new QuestaoDTO();
                    questao.setPergunta((String) questionData.get("questionText"));
                    questao.setScore((String) questionData.get("questionScore"));
                    questao.setOptions((List<Map<String, Object>>) questionData.get("options"));
                    return questao;
                  })
              .collect(Collectors.toList());
    }
  }
}
