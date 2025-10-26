package com.model.auth;

import java.io.*;

import com.singleton.UserRegister;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Register {
    private  File folder = new File(System.getProperty("user.home") + "CapyCourses/capycourse");
    public void isRegister(UserRegister user){
        if (!folder.exists()) folder.mkdirs();

        File file = new File(folder, "userLogin.csv");

         try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(user.getUserName() + "," + user.getUserEmail() + "," + user.getPassword() + "," + user.getType() + "," + user.getDate()+ ","+ user.getCpf()+ ","+ user.getPhone()+ "," + user.getEducation());
            writer.newLine();
            user.destroyInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean emailExists(String email){
        if (!folder.exists()) return false;

        File file = new File(folder, "userLogin.csv");

         try (BufferedReader br = new BufferedReader(
                new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (email.equals(values[2])) return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
