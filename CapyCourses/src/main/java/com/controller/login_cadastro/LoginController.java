package com.controller.login_cadastro;

import com.model.login_cadastro.Login;
import com.model.login_cadastro.User;
import com.model.login_cadastro.Student;
import com.model.login_cadastro.Teacher;
import com.dao.UserDAO;
import com.dao.StudentDAO;
import com.dao.TeacherDAO;
import com.singleton.UserSession;

public class LoginController {
    private final UserDAO userDAO = new UserDAO();
    private final StudentDAO studentDAO = new StudentDAO();
    private final TeacherDAO teacherDAO = new TeacherDAO();

    public String isCheck(String email, String password) {
        try {
<<<<<<< HEAD
            // Buscar usuário pelo email
=======
>>>>>>> 4a68bd8 (Sprint 00 - Resolvido Bug Cadastro Incompleto)
            User user = userDAO.buscarPorEmail(email);
            
            if (user == null || !user.getPassword().equals(password)) {
                return "false";
            }

<<<<<<< HEAD
            UserSession.getInstance().setUserEmail(email);
            UserSession.getInstance().setUserName(user.getName());

            // Verificar tipo de usuário e se cadastro está completo
            if (user.getTypeUser().equals("STUDENT")) {
                Student student = studentDAO.buscarPorUserId(user.getId());
                return (student == null) ? "incomplete student" : "true";
            } else {
                Teacher teacher = teacherDAO.buscarPorUserId(user.getId());
                return (teacher == null) ? "incomplete teacher" : "true";
            }
=======
            UserSession session = UserSession.getInstance();
            System.out.println("Definindo email na sessão: " + email);
            session.setUserEmail(email);
            session.setUserName(user.getName());

            String result;
            if (user.getTypeUser().equalsIgnoreCase("STUDENT")) {
                Student student = studentDAO.buscarPorUserId(user.getId());
                if (student != null) {
                    session.setRegisterIncomplet("true");
                    result = "true";
                } else {
                    session.setRegisterIncomplet("incomplete student");
                    result = "incomplete student";
                }
            } else if (user.getTypeUser().equalsIgnoreCase("TEACHER")) {
                Teacher teacher = teacherDAO.buscarPorUserId(user.getId());
                if (teacher != null) {
                    session.setRegisterIncomplet("true");
                    result = "true";
                } else {
                    session.setRegisterIncomplet("incomplete teacher");
                    result = "incomplete teacher";
                }
            } else {
                result = "false";
            }
            
            return result;
>>>>>>> 4a68bd8 (Sprint 00 - Resolvido Bug Cadastro Incompleto)
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
    }
}
