package com.model.login_cadastro;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import com.model.Student;

public class Cadastro {
    public Cadastro(String name, String email, String password, LocalDateTime dateRegister,String typeUser){
          try (BufferedWriter writer = new BufferedWriter(new FileWriter("CapyCourses\\src\\main\\resources\\com\\bd\\bd_user.csv", true))) {
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
