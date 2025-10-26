package com.dto;

import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestaoDTO {
  private String pergunta;
  private List<String> alternativas;
  private int alternativaCorreta;
  private List<Integer> alternativasCorretas;
  private String tipo;
  private String score;
  private List<Map<String, Object>> options;
  private String respostaEsperada;
  private String criteriosAvaliacao;

  public QuestaoDTO() {}

  @Override
  public String toString() {
    return "QuestaoDTO{"
        + "pergunta='"
        + pergunta
        + '\''
        + ", alternativas="
        + alternativas
        + ", alternativaCorreta="
        + alternativaCorreta
        + ", tipo='"
        + tipo
        + '\''
        + ", score='"
        + score
        + '\''
        + ", options="
        + options
        + '}';
  }

  public String getScore() {
    return score;
  }

  public void setScore(String score) {
    this.score = score;
  }

  public List<Map<String, Object>> getOptions() {
    return options;
  }

  public void setOptions(List<Map<String, Object>> options) {
    this.options = options;
  }
}
