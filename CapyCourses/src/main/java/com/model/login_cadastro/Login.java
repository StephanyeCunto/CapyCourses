package com.model.login_cadastro;

import com.dao.StudentDAO;
import com.dao.TeacherDAO;
import com.dao.UserDAO;
import com.service.EmailService;
import com.singleton.UserSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Login {
  private String user;
  private String password;

  private final UserDAO userDAO = new UserDAO();
  private final StudentDAO studentDAO = new StudentDAO();
  private final TeacherDAO teacherDAO = new TeacherDAO();

  public String isCheck() {
    try {
      User userFound = userDAO.buscarPorEmail(user);

      if (userFound == null || !userFound.getPassword().equals(password)) {
        return "false";
      }

      // Configura a sessão do usuário
      UserSession session = UserSession.getInstance();
      session.setUserEmail(user);
      session.setUserName(userFound.getName());

      // Verifica se é um cadastro incompleto
      if (userFound.getTypeUser().equalsIgnoreCase("STUDENT")) {
        Student student = studentDAO.buscarPorUserId(userFound.getId());
        if (student == null) {
          session.setRegisterIncomplet("Student");
          session.setUserEmail(user);
          System.out.println("Email mantido na sessão: " + session.getUserEmail());
          return "incomplete student";
        }
      } else if (userFound.getTypeUser().equalsIgnoreCase("TEACHER")) {
        Teacher teacher = teacherDAO.buscarPorUserId(userFound.getId());
        if (teacher == null) {
          session.setRegisterIncomplet("Teacher");
          session.setUserEmail(user);
          System.out.println("Email mantido na sessão: " + session.getUserEmail());
          return "incomplete teacher";
        }
      }

      // Cadastro completo
      session.setRegisterIncomplet("complete");
      return "true";

    } catch (Exception e) {
      e.printStackTrace();
      return "false";
    }
  }

  public boolean recuperarSenha(String email) {
    try {
      User userFound = userDAO.buscarPorEmail(email);

      if (userFound == null) {
        return false;
      }

      String novaSenha = gerarSenhaTemporaria();
      userFound.setPassword(novaSenha);
      userDAO.atualizar(userFound);

      EmailService emailService = new EmailService();
      emailService.enviarEmailRecuperacao(email, novaSenha);

      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  private String gerarSenhaTemporaria() {
    String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    StringBuilder senha = new StringBuilder();
    java.util.Random rnd = new java.util.Random();

    for (int i = 0; i < 8; i++) {
      senha.append(chars.charAt(rnd.nextInt(chars.length())));
    }

    return senha.toString();
  }
}
