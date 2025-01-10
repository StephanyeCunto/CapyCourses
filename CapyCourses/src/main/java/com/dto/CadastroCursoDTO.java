package com.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CadastroCursoDTO {
    // Informações Básicas
    private String titulo;
    private String descricao;
    private String categoria;
    private String nivel;
    private List<String> tags;
    
    // Módulos
    private List<ModuloDTO> modulos;
    
    // Configurações
    private String duracaoTotal;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private boolean semDataFinal;
    private boolean emitirCertificado;
    private boolean exigirNotaMinima;
    private String visibilidade;
}

@Getter
@Setter
class ModuloDTO {
    private String titulo;
    private String descricao;
    private String duracao;
    private int numeroModulo;
    private List<AulaDTO> aulas;
    private List<QuestionarioDTO> questionarios;
}

@Getter
@Setter
class AulaDTO {
    private String titulo;
    private String descricao;
    private String duracao;
    private int numeroAula;
}

@Getter
@Setter
class QuestionarioDTO {
    private String titulo;
    private List<QuestaoDTO> questoes;
    private double notaMinima;
}

@Getter
@Setter
class QuestaoDTO {
    private String pergunta;
    private List<String> alternativas;
    private int alternativaCorreta;
} 