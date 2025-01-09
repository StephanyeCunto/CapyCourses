package com.controller.login_cadastro;

import java.time.LocalDateTime;
import com.model.login_cadastro.User;
import com.dao.UserDAO;
import com.singleton.UserSession;

public class CadastroController {
    private final UserDAO userDAO = new UserDAO();

    public String cadastrar(String name, String email, String password, LocalDateTime dateRegister, String typeUser) {
        try {
            if (userDAO.buscarPorEmail(email) != null) {
                return "email_exists";
            }

            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            user.setDateRegister(dateRegister);
            user.setTypeUser(typeUser);

            userDAO.salvar(user);
            
            // Configura a sessão após salvar com sucesso
            UserSession session = UserSession.getInstance();
            session.setUserEmail(email);
            session.setUserName(name);
            
            // Define o tipo de cadastro incompleto baseado no tipo de usuário
            if (typeUser.equalsIgnoreCase("STUDENT")) {
                session.setRegisterIncomplet("Student");
                return "incomplete student";
            } else if (typeUser.equalsIgnoreCase("TEACHER")) {
                session.setRegisterIncomplet("Teacher");
                return "incomplete teacher";
            }

            return "success";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}
