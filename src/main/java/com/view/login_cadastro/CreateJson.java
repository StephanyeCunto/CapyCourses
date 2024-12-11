package com.view.login_cadastro;

import java.io.File;
import java.io.IOException;
import com.model.login_cadastro.Login;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CreateJson {
    public static void saveLoginData(String email, String senha, String filePath) {
        ObjectMapper mapper = new ObjectMapper();

        Login login = new Login(email, senha);

        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), login);
        } catch (IOException e) {
        }
    }

    public static void verifyAndDeleteLoginData(String email, String senha, String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Login savedLogin = mapper.readValue(new File(filePath), Login.class);
            if (savedLogin.getUser().equals(email) && savedLogin.getPassword().equals(senha)) {
                new File(filePath).delete();
            }
        } catch (IOException e) {
        }
    }

    public static String getSavedEmail(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Login savedLogin = mapper.readValue(new File(filePath), Login.class);
            return savedLogin.getUser();
        } catch (IOException e) {
            System.out.println("erro"+e);
            return null;
        }
    }

    public static String getSavedPassword(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Login savedLogin = mapper.readValue(new File(filePath), Login.class);
            return savedLogin.getPassword();
        } catch (IOException e) {
            return null;
        }
    }

}
