package com.model.login_cadastro;

import com.singleton.UserSession;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class CadastroStudent {
  public CadastroStudent(
      Date dateOfBirth, String CPF, String telephone, String education, String areaOfInterest) {
    try (BufferedWriter writer =
        new BufferedWriter(
            new FileWriter("capycourses/src/main/resources/com/bd/bd_student.csv", true))) {
      writer.write(
          UserSession.getInstance().getUserEmail()
              + ","
              + dateOfBirth
              + ","
              + CPF
              + ","
              + telephone
              + ","
              + education
              + ","
              + areaOfInterest
              + ","
              + ",");
      writer.newLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
