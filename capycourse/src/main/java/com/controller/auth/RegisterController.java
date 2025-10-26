package com.controller.auth;

import com.model.auth.Register;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RegisterController {
    public String isRegister(String userName,String userEmail,String password, String userType){
        Register rgt = new Register();
        return rgt.isRegister(userName, userEmail, password, userType);
    }
}
