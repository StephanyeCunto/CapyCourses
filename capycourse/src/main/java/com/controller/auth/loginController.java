package com.controller.auth;

import com.model.auth.dao.UserDAO;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LoginController {
    private UserDAO userDAO = new UserDAO();

    public boolean isCheck(String userEmail,String password){
        return userDAO.login(userEmail, password);
    }
}
