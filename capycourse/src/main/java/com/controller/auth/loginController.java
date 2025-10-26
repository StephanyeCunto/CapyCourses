package com.controller.auth;

import com.model.auth.Login;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LoginController {
    public boolean isCheck(String userEmail,String password){
        Login lgn = new Login();
        return lgn.isCheck(userEmail,password);
    }
}
