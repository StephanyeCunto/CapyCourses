package com.controller.login_cadastro;

import com.dao.StudentDAO;
import com.dao.TeacherDAO;
import com.dao.UserDAO;
import com.model.login_cadastro.Login;
import com.model.login_cadastro.Student;
import com.model.login_cadastro.Teacher;
import com.model.login_cadastro.User;
import com.singleton.UserSession;

public class LoginController {
  private final UserDAO userDAO = new UserDAO();
  private final StudentDAO studentDAO = new StudentDAO();
  private final TeacherDAO teacherDAO = new TeacherDAO();

  public String isCheck(String email, String password) {
    try {
      User user = userDAO.buscarPorEmail(email);

      if (user == null || !user.checkPassword(password)) {
        return "false";
      }

      UserSession session = UserSession.getInstance();
      session.setUserEmail(email);
      session.setUserName(user.getName());

      if (user.getTypeUser().equalsIgnoreCase("STUDENT")) {
        Student student = studentDAO.buscarPorUserId(user.getId());
        if (student == null) {
          session.setRegisterIncomplet("incomplete student");
          return "incomplete student";
        }
      } else if (user.getTypeUser().equalsIgnoreCase("TEACHER")) {
        Teacher teacher = teacherDAO.buscarPorUserId(user.getId());
        if (teacher == null) {
          session.setRegisterIncomplet("incomplete teacher");
          return "incomplete teacher";
        }
      }

      session.setRegisterIncomplet("true");
      return "true";

    } catch (Exception e) {
      e.printStackTrace();
      return "false";
    }
  }

  public String getUserType(String email) {
    try {
      User user = userDAO.buscarPorEmail(email);
      if (user != null) {
        return user.getTypeUser();
      }
      return null;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public boolean recuperarSenha(String email) {
    Login login = new Login();
    return login.recuperarSenha(email);
  }
}
