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