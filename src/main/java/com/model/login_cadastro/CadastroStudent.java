package com.model.login_cadastro;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import com.UserSession;

public class CadastroStudent {
    public CadastroStudent(Date dateOfBirth, String CPF, int telephone, String education, String areaOfInterest) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("CapyCourses\\src\\main\\resources\\com\\bd\\bd_user.csv", true))) {
            writer.write(UserSession.getInstance().getUserName() + "," + dateOfBirth + "," + CPF + "," + telephone + ","+ education + "," + areaOfInterest + "," + ",");
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
