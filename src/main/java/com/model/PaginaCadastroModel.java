package com.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class PaginaCadastroModel {
    public PaginaCadastroModel(String name, String email, String password, LocalDateTime dateRegister,String typeUser){
          try (BufferedWriter writer = new BufferedWriter(new FileWriter("src\\main\\resources\\com\\bd_user.csv", true))) {
            writer.write(name+","+email+","+password+","+dateRegister+","+typeUser);
            writer.newLine();
            if(typeUser.equals("student")){
                new Student(email);
            }
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }
}
