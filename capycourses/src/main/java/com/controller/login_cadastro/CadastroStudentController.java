package com.controller.login_cadastro;

import java.util.Date;

import com.model.login_cadastro.CadastroStudent;

public class CadastroStudentController {
    public CadastroStudentController(Date dateOfBirth, String CPF, String telephone, String education, String areaOfInterest){
        new CadastroStudent(dateOfBirth,  CPF, telephone, education, areaOfInterest);
    }
}
