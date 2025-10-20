package com.view.auth.valid;

public interface AuthValid {
    String NAME_REGEX = "^[A-Za-z]+{3,} [A-Za-z]+{3,}$";
    String USER_REGEX = "^[A-Za-z]+[A-Za-z0-9+_.-]*@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,20}$";

    void init();
    boolean isCheck();
}
