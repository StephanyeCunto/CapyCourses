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
            System.out.println("Iniciando cadastro de professor com email: " + email);
            
            if (email == null || email.trim().isEmpty()) {
                System.out.println("Email não encontrado ou vazio");
                return false;
            }

            User user = userDAO.buscarPorEmail(email);
            if (user == null) {
                System.out.println("Usuário não encontrado para o email: " + email);
                return false;
            }
            System.out.println("Usuário encontrado com ID: " + user.getId());

            Teacher teacher = new Teacher();
            teacher.setUser(user);
            teacher.setDateOfBirth(dateOfBirth);
            teacher.setCpf(cpf);
            teacher.setTelephone(telephone);
            teacher.setEducation(education);
            teacher.setAreaOfInterest(areaOfInterest);

            System.out.println("Tentando salvar professor para o usuário ID: " + user.getId());
            teacherDAO.salvar(teacher);
            
            Teacher savedTeacher = teacherDAO.buscarPorUserId(user.getId());
            if (savedTeacher != null) {
                System.out.println("Professor salvo com sucesso!");
                UserSession.getInstance().setRegisterIncomplet("false");
                return true;
            }
            
            System.out.println("Falha ao verificar professor salvo");
            return false;
            
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar professor: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
