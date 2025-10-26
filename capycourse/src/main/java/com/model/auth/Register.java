package com.model.auth;

import java.io.*;

import com.singleton.UserRegister;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Register {
    public void isRegister(UserRegister user){

        File folder = new File(System.getProperty("user.home") + "CapyCourses/capycourse");
        if (!folder.exists()) folder.mkdirs();

        File file = new File(folder, "userLogin.csv");

         try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(file, true))) {
            writer.write(user.getUserName() + "," + user.getUserEmail() + "," + user.getPassword() + "," + user.getType() + "," + user.getDate()+ ","+ user.getCpf()+ ","+ user.getPhone()+ "," + user.getEducation());
            writer.newLine();
            user.destroyInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
