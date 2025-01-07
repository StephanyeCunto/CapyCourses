package com.singleton;

public class UserSession {
    private static UserSession instance;
    private String userEmail;
    private String registerIncomplet;
    private boolean started = false;
    private String userName;

    private UserSession() {}

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setRegisterIncomplet(String status) {
        this.registerIncomplet = status;
    }

    public String getRegisterIncomplet() {
        return registerIncomplet;
    }

    public void clearSession() {
        this.userEmail = null;
        this.registerIncomplet = null;
    }

    public boolean getStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}