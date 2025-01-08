package com.controller.login_cadastro;

import java.util.Date;
import com.model.login_cadastro.Student;
import com.model.login_cadastro.User;
import com.dao.StudentDAO;
import com.dao.UserDAO;
import com.singleton.UserSession;

public class CadastroStudentController {
    private final StudentDAO studentDAO = new StudentDAO();
    private final UserDAO userDAO = new UserDAO();

    public boolean cadastrarStudent(String email, Date dateOfBirth, String cpf, 
            String telephone, String education, String areaOfInterest) {
        try {
            // Buscar o usuário pelo email
            User user = userDAO.buscarPorEmail(email);
            if (user == null) return false;

            // Criar o estudante
            Student student = new Student();
            student.setUser(user);
            student.setDateOfBirth(dateOfBirth);
            student.setCpf(cpf);
            student.setTelephone(telephone);
            student.setEducation(education);
            student.setAreaOfInterest(areaOfInterest);

            // Salvar no banco
            studentDAO.salvar(student);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
