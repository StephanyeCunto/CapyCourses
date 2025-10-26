package com.singleton;

import java.time.LocalDate;

import lombok.*;

@Getter
@Setter
public class User {
    private static User instance;
    private String userName, userEmail, password, type, cpf, phone, education;
    private LocalDate date;

    public static User getInstance() {
        if (instance == null) instance = new User();
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }
}
