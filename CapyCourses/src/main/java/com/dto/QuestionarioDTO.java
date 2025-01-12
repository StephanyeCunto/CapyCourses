package com.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionarioDTO {
    private String titulo;
    private List<QuestaoDTO> questoes;
    private double notaMinima;
    private String descricao;

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
} 