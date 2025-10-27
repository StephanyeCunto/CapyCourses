package com.controller.auth;

import com.model.auth.dao.UserDAO;
import com.model.auth.entity.UserEntity;
import com.singleton.User;

import org.mindrot.jbcrypt.BCrypt; 

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RegisterController {

    private UserDAO userDAO = new UserDAO();

    public void register(User user) {
        UserEntity entity = new UserEntity();
        entity.setUserName(user.getUserName());
        entity.setEmail(user.getUserEmail());
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        entity.setPassword(hashedPassword);
        entity.setType(user.getType());
        entity.setCpf(user.getCpf());
        entity.setPhone(user.getPhone());
        entity.setEducation(user.getEducation());
        entity.setDate(user.getDate().toString());

        userDAO.save(entity);
    }

    public boolean emailExists(String email){
        return userDAO.emailExists(email);
    }
}
