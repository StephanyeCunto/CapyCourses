package com.controller.auth;

import com.model.auth.Register;
import com.singleton.UserRegister;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RegisterController {
    public void isRegister(UserRegister user){
        Register rgt = new Register();
        rgt.isRegister(user);
    }

    public boolean emailExists(String email){
        Register rgt = new Register();
        return rgt.emailExists(email);
    }
}
