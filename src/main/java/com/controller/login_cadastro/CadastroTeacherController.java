package com.controller.login_cadastro;

import java.util.Date;

import com.model.login_cadastro.CadastroTeacher;

public class CadastroTeacherController {
    public CadastroTeacherController(Date dateOfBirth, String CPF, long telephone, String education, String areaOfInterest){
        new CadastroTeacher(dateOfBirth,  CPF, telephone, education, areaOfInterest);
    }
}
