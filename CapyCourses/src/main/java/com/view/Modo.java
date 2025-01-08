package com.view;

import lombok.Getter;

@Getter
public class Modo {
    private static Modo instance;
    private boolean modo=true;

    public static Modo getInstance() {
        if (instance == null) {
            instance = new Modo();  
        }
        return instance;
    }

    public void setModo(){
        modo=(!modo);
    }

    public boolean getModo(){
        return modo;
    }
}