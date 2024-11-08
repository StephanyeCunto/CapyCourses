package com.model.login_cadastro;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Login {
    private String user;
    private String password;

    public String isCheck() {
        try (BufferedReader br = new BufferedReader(
                new FileReader("CapyCourses\\src\\main\\resources\\com\\bd\\bd_user.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(",");
                if (user.equals(elements[1])) {
                    if (password.equals(elements[2])) {    
                        if (completeRegistration(elements[4],elements[1])) {
                            return "true";
                        }
                        return "incomplete " + elements[4];
                    }
                }
            }
            return "false";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "false";
    }

    private boolean completeRegistration(String type,String email) {
        if (type.trim().equals("student")) {
            return isCheckStudent(email);
        }else if(type.trim().equals("teacher")){
            return isCheckTeacher(email);
        }

        return false;
    }

    private boolean isCheckStudent(String email) {
        try (BufferedReader br = new BufferedReader(
                new FileReader("CapyCourses\\src\\main\\resources\\com\\bd\\bd_student.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (email.equals(values[0])) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isCheckTeacher(String email) {
        try (BufferedReader br = new BufferedReader(
                new FileReader("CapyCourses\\src\\main\\resources\\com\\bd\\bd_teacher.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (email.equals(values[0])) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}