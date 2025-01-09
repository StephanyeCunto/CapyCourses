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
<<<<<<< HEAD
            // Buscar o usuário pelo email
            User user = userDAO.buscarPorEmail(email);
            if (user == null) return false;

            // Criar o estudante
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
            Student student = new Student();
            student.setUser(user);
            student.setDateOfBirth(dateOfBirth);
            student.setCpf(cpf);
            student.setTelephone(telephone);
            student.setEducation(education);
            student.setAreaOfInterest(areaOfInterest);

<<<<<<< HEAD
            // Salvar no banco
            studentDAO.salvar(student);
            return true;
        } catch (Exception e) {
=======
            System.out.println("Tentando salvar estudante para o usuário ID: " + user.getId());
            studentDAO.salvar(student);
            
            Student savedStudent = studentDAO.buscarPorUserId(user.getId());
            if (savedStudent != null) {
                System.out.println("Estudante salvo com sucesso!");
                UserSession.getInstance().setRegisterIncomplet("true");
                return true;
            }
            System.out.println("Falha ao verificar estudante salvo");
            return false;
            
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar estudante: " + e.getMessage());
>>>>>>> 4a68bd8 (Sprint 00 - Resolvido Bug Cadastro Incompleto)
            e.printStackTrace();
            return false;
        }
    }
}
