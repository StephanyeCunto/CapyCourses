package com.view.login_cadastro;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.controller.login_cadastro.LoginJsonController;

public class CreateJson {
    public static void saveLoginData(String userName, String userPassword, String filePath) {
        ObjectMapper mapper = new ObjectMapper();

        LoginJsonController loginJsonController = new LoginJsonController(userName, userPassword);

        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), loginJsonController);
        } catch (IOException e) {
        }
    }

    public static void verifyAndDeleteLoginData(String userName, String userPassword, String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            LoginJsonController savedLogin = mapper.readValue(new File(filePath), LoginJsonController.class);
            if (savedLogin.getUserName().equals(userName) && savedLogin.getUserPassword().equals(userPassword)) {
                new File(filePath).delete();
            }
        } catch (IOException e) {
        }
    }

    public static String getSavedName(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            LoginJsonController savedLogin = mapper.readValue(new File(filePath), LoginJsonController.class);
            return savedLogin.getUserName();
        } catch (IOException e) {
            System.out.println("erro"+e);
            return null;
        }
    }

    public static String getSavedPassword(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            LoginJsonController savedLogin = mapper.readValue(new File(filePath), LoginJsonController.class);
            return savedLogin.getUserPassword();
        } catch (IOException e) {
            return null;
        }
    }

}
