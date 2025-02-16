package com.controller.login_cadastro;

import com.dao.StudentDAO;
import com.dao.UserDAO;
import com.model.login_cadastro.Student;
import com.model.login_cadastro.User;
import com.singleton.UserSession;
import java.util.Date;

public class CadastroStudentController {
  private final StudentDAO studentDAO = new StudentDAO();
  private final UserDAO userDAO = new UserDAO();

  public boolean cadastrarStudent(
      String email,
      Date dateOfBirth,
      String cpf,
      String telephone,
      String education,
      String areaOfInterest) {
    try {
      if (email == null) {
        System.out.println("Email não encontrado");
        return false;
      }

      User user = userDAO.buscarPorEmail(email);
      if (user == null) {
        System.out.println("Usuário não encontrado para o email: " + email);
        return false;
      }

      Student student = new Student();
      student.setUser(user);
      student.setDateOfBirth(dateOfBirth);
      student.setCpf(cpf);
      student.setTelephone(telephone);
      student.setEducation(education);
      student.setAreaOfInterest(areaOfInterest);

      studentDAO.salvar(student);

      Student savedStudent = studentDAO.buscarPorUserId(user.getId());
      if (savedStudent != null) {
        UserSession.getInstance().setRegisterIncomplet("false");
        return true;
      }
      return false;

    } catch (Exception e) {
      System.out.println("Erro ao cadastrar estudante: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }
}
