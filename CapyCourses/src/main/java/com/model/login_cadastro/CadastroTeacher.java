package com.model.login_cadastro;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import com.singleton.UserSession;

public class CadastroTeacher {
        public CadastroTeacher(Date dateOfBirth, String CPF, String telephone, String education, String areaOfInterest) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("capycourses/src/main/resources/com/bd/bd_teacher.csv", true))) {
            writer.write(UserSession.getInstance().getUserEmail() + "," + dateOfBirth + "," + CPF + "," + telephone + ","+ education + "," + areaOfInterest);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}