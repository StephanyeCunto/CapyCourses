package com.controller.professor;

import com.model.professor.CadastroCurso;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class CadastroCursoController {

    public CadastroCursoController(String title, String descrition, String category, String level, String tags,
            File image, List<Map<String, Object>> modulesData,
            LocalDate dateStart, LocalDate dateEnd, String durationTotal, boolean isDateEnd, boolean isCertificate,
            boolean isGradeMiniun, Object ComboBoxVisibily) {
        new CadastroCurso(title, descrition, category, level, tags, modulesData, dateStart, dateEnd, durationTotal, isDateEnd, isCertificate,
        isGradeMiniun, ComboBoxVisibily);
    }
}
