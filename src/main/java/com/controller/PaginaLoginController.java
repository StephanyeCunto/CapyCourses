package com.controller;

import com.model.PaginaLoginModel;

public class PaginaLoginController{
    public boolean isCheck(String userName, String userPassword){
        PaginaLoginModel cl= new PaginaLoginModel(userName, userPassword);
        return cl.isCheck();
    }

}
