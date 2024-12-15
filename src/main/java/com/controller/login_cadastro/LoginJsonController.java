package com.controller.login_cadastro;

import com.model.login_cadastro.Login;

import lombok.*;

@Data
public class LoginJsonController {
private String userName;
private String userPassword;

    public LoginJsonController(String userName, String userPassword) {
        Login cl = new Login(userName, userPassword);
        
        if (cl.isCheck().equals("true")) {
            this.userName = userName;
            this.userPassword = userPassword;
        }
    }
}
