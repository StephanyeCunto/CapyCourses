package com.singleton;

import java.time.LocalDate;

import lombok.*;

@Getter
@Setter
public class UserRegister {
    private static UserRegister instance;
    private String userName, userEmail, password, type, cpf, phone, education;
    private LocalDate date;

    public static UserRegister getInstance() {
        if (instance == null) instance = new UserRegister();
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }
}
