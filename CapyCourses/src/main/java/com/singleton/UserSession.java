package com.singleton;

public class UserSession {
    private static UserSession instance;
    private String userEmail;
    private String registerIncomplet;
    private boolean started = false;
    private String userName;
    private String userType;
    private String currentCourseTitle;

    private UserSession() {}

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public String getUserEmail() {
        System.out.println("Recuperando email da sessão: " + userEmail); // Debug
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        System.out.println("Definindo email na sessão: " + userEmail); // Debug
        this.userEmail = userEmail;
    }

    public void setRegisterIncomplet(String status) {
        System.out.println("Definindo status de registro: " + status); // Debug
        this.registerIncomplet = status;
    }

    public String getRegisterIncomplet() {
        System.out.println("Recuperando status de registro: " + registerIncomplet); // Debug
        return registerIncomplet;
    }

    public void clearSession() {
        System.out.println("Limpando sessão - Email anterior: " + userEmail); // Debug
        this.userEmail = null;
        this.registerIncomplet = null;
        this.userName = null;
        this.started = false;
    }

    public boolean isSessionValid() {
        return userEmail != null && !userEmail.trim().isEmpty();
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

    public String getUserType() {

        return userType;

    }

    public void setUserType(String userType) {

        this.userType = userType;

    }

    public void setCurrentCourseTitle(String title) {
        this.currentCourseTitle = title;
    }

    public String getCurrentCourseTitle() {
        return currentCourseTitle;
    }
}