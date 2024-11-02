package com.controller.login_cadastro;

import java.util.Date;

import com.model.login_cadastro.CadastroStudent;

public class CadastroStudentController {

    public void CadastroStudentController(Date dateOfBirth, String CPF, int telephone, String education, String areaOfInterest){
        new CadastroStudent(dateOfBirth,  CPF, telephone, education, areaOfInterest);
    }
}
