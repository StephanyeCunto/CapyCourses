package com.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModuloDTO {
  private String titulo;
  private String descricao;
  private String duracao;
  private int numeroModulo;
  private List<AulaDTO> aulas;
  private List<QuestionarioDTO> questionarios;
}
