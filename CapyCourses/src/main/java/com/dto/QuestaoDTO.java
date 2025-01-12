package com.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestaoDTO {
    private String pergunta;
    private List<String> alternativas;
    private int alternativaCorreta;
} 