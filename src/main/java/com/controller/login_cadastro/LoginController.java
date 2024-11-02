package com.controller.login_cadastro;

import com.model.login_cadastro.Login;

public class LoginController{
    public boolean isCheck(String userName, String userPassword){
        Login cl= new Login(userName, userPassword);
        return cl.isCheck();
    }

}
