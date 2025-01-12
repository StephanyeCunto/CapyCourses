package com.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AulaDTO {
    private String titulo;
    private String descricao;
    private String duracao;
    private int numeroAula;
    private Integer moduleNumber;
    private String materiais;
    private String videoLink;

    public Integer getModuleNumber() {
        return moduleNumber;
    }

    public void setModuleNumber(Integer moduleNumber) {
        this.moduleNumber = moduleNumber;
    }

    public String getMateriais() {
        return materiais;
    }

    public void setMateriais(String materiais) {
        this.materiais = materiais;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }
} 