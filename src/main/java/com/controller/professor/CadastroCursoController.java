package com.controller.professor;

import com.model.professor.CadastroCurso;

import java.io.File;

public class CadastroCursoController {
    public CadastroCursoController(String title,String descrition,String category,String level,String tags,File image){
        new CadastroCurso(title, descrition, category, level, tags);
    }
}
