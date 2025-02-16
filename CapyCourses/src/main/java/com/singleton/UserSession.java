package com.singleton;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSession {
  private static UserSession instance;
  private String userEmail;
  private String registerIncomplet;
  private boolean started = false;
  private String userName;
  private String userType;
  private String CurrentCourseTitle;

  public static UserSession getInstance() {
    if (instance == null) {
      instance = new UserSession();
    }
    return instance;
  }

  public Boolean getStarted() {
    if (!started) {
      this.started = true;
      return false;
    }
    return true;
  }

  public void clearSession() {
    userEmail = null;
  }
}
