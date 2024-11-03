package com.model.login_cadastro;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.UserSession;

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
                        if (completeRegistration(elements[4])) {
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

    private boolean completeRegistration(String type) {
        if (type == "student") {
            return isCheckStudent();
        }
        return isCheckTeacher();
    }

    private boolean isCheckStudent() {
        try (BufferedReader br = new BufferedReader(
                new FileReader("CapyCourses\\src\\main\\resources\\com\\bd\\bd_student.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (UserSession.getInstance().getUserName() == values[0]) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isCheckTeacher() {
        try (BufferedReader br = new BufferedReader(
                new FileReader("CapyCourses\\src\\main\\resources\\com\\bd\\bd_teacher.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (UserSession.getInstance().getUserEmail() == values[0]) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}