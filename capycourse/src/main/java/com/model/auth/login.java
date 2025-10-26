package com.model.auth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Login {
    private File folder = new File(System.getProperty("user.home") + "CapyCourses/capycourse");

    public boolean isCheck(String emailUser, String password){
        if (!folder.exists()) return false;

        File file = new File(folder, "userLogin.csv");

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (emailUser.equals(values[2]) && password.equals(values[3])) return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
