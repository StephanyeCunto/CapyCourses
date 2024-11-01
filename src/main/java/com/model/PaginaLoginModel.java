package com.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PaginaLoginModel{
    private String user;
    private String password;

    public boolean isCheck(){
        try (BufferedReader br = new BufferedReader(new FileReader("src\\main\\resources\\com\\bd_user.csv"))){
            String line;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(",");
                if(user.equals(elements[1])){
                    if(password.equals(elements[2])){
                        return true;
                    }
                }
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}