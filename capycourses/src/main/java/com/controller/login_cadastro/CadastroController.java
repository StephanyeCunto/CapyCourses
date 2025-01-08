package com.controller.login_cadastro;

import java.time.LocalDateTime;

import com.model.login_cadastro.Cadastro;

public class CadastroController {
    public CadastroController(String name, String email, String password, LocalDateTime dateRegister,String typeUser) {
        new Cadastro(name,email,password,dateRegister,typeUser);
    }
}
