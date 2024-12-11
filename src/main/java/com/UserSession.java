package com;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import lombok.Getter;

@Getter
public class UserSession {
    private static UserSession instance;
    private String userEmail;
    private String registerIncomplet;
    private Boolean started=false;

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void setUserEmail(String email) {
        this.userEmail = email.trim();
    }

    public void setRegisterIncomplet(String registerIncomplet){
        this.registerIncomplet=registerIncomplet;
    }

    public String getRegisterIncomplet(){
        return registerIncomplet;
    }

    public Boolean getStarted(){
        if(!started){
            this.started=true;
            return false;
        }
        return true;
    }

    public String getUserName(){
         try (BufferedReader br = new BufferedReader(new FileReader("CapyCourses\\src\\main\\resources\\com\\bd\\bd_user.csv"))){
            String line;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(",");
                if(userEmail.equals(elements[1])){
                    return elements[0];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "false";
    }

    public void clearSession() {
        userEmail = null;
    }
}