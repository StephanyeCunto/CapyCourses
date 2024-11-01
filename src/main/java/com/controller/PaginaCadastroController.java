package com.controller;

import java.time.LocalDateTime;

import com.model.PaginaCadastroModel;

public class PaginaCadastroController {
    public PaginaCadastroController(String name, String email, String password, LocalDateTime dateRegister,String typeUser) {
        new PaginaCadastroModel(name,email,password,dateRegister,typeUser);
    }
}
