package com.controller.login_cadastro;

import java.time.LocalDateTime;
import com.model.login_cadastro.User;
import com.dao.UserDAO;

public class CadastroController {
    private final UserDAO userDAO = new UserDAO();

    public boolean cadastrar(String name, String email, String password, LocalDateTime dateRegister, String typeUser) {
        try {
        
            if (userDAO.buscarPorEmail(email) != null) {
                
                return false;
            }

            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            user.setDateRegister(dateRegister);
            user.setTypeUser(typeUser);

            userDAO.salvar(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
