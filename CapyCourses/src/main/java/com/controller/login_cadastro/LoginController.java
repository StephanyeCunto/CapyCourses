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
            // Buscar usuário pelo email
            User user = userDAO.buscarPorEmail(email);
            
            if (user == null || !user.getPassword().equals(password)) {
                return "false";
            }

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
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
    }
}
