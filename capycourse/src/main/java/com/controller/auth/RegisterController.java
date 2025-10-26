package com.controller.auth;

import com.model.auth.dao.UserDAO;
import com.model.auth.entity.UserEntity;
import com.singleton.User;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RegisterController {

    private UserDAO userDAO = new UserDAO();

    public void register(User user) {
        UserEntity entity = new UserEntity();
        entity.setUserName(user.getUserName());
        entity.setEmail(user.getUserEmail());
        entity.setPassword(user.getPassword());
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
