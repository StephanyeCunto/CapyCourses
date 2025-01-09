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
<<<<<<< HEAD
            
            User user = userDAO.buscarPorEmail(email);
            if (user == null) return false;

           
=======
            // Pega o email da sessão do usuário
            if (email == null) {
                System.out.println("Email não encontrado");
                return false;
            }

            User user = userDAO.buscarPorEmail(email);
            if (user == null) {
                System.out.println("Usuário não encontrado para o email: " + email);
                return false;
            }

>>>>>>> 4a68bd8 (Sprint 00 - Resolvido Bug Cadastro Incompleto)
            Teacher teacher = new Teacher();
            teacher.setUser(user);
            teacher.setDateOfBirth(dateOfBirth);
            teacher.setCpf(cpf);
            teacher.setTelephone(telephone);
            teacher.setEducation(education);
            teacher.setAreaOfInterest(areaOfInterest);

<<<<<<< HEAD
           
            teacherDAO.salvar(teacher);
            return true;
        } catch (Exception e) {
=======
            System.out.println("Tentando salvar professor para o usuário ID: " + user.getId());
            teacherDAO.salvar(teacher);
            
            Teacher savedTeacher = teacherDAO.buscarPorUserId(user.getId());
            if (savedTeacher != null) {
                System.out.println("Professor salvo com sucesso!");
                UserSession.getInstance().setRegisterIncomplet("true");
                return true;
            }
            System.out.println("Falha ao verificar professor salvo");
            return false;
            
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar professor: " + e.getMessage());
>>>>>>> 4a68bd8 (Sprint 00 - Resolvido Bug Cadastro Incompleto)
            e.printStackTrace();
            return false;
        }
    }
}
