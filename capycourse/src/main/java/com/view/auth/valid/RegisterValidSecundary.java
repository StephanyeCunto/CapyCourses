package com.view.auth.valid;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RegisterValidSecundary implements AuthValid{
    @Override
    public void init(){

    }

    @Override
    public  boolean isCheck(){
        return true;
    } 
}
