package com.controller.login_cadastro;

import java.util.Date;
import com.model.login_cadastro.Teacher;
import com.model.login_cadastro.User;
import com.dao.TeacherDAO;
import com.dao.UserDAO;
import com.singleton.UserSession;

public class CadastroTeacherController {
    private final TeacherDAO teacherDAO = new TeacherDAO();
    private final UserDAO userDAO = new UserDAO();

    public boolean cadastrarTeacher(String email, Date dateOfBirth, String cpf, 
            String telephone, String education, String areaOfInterest) {
        try {
            
            User user = userDAO.buscarPorEmail(email);
            if (user == null) return false;

           
            Teacher teacher = new Teacher();
            teacher.setUser(user);
            teacher.setDateOfBirth(dateOfBirth);
            teacher.setCpf(cpf);
            teacher.setTelephone(telephone);
            teacher.setEducation(education);
            teacher.setAreaOfInterest(areaOfInterest);

           
            teacherDAO.salvar(teacher);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
