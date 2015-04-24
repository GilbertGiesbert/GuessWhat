package com.joern.guesswhat.activity.login;

import com.joern.guesswhat.model.User;

/**
 * Created by joern on 24.04.2015.
 */
public class LoginResult {

    private boolean loginSuccessful = false;
    private User user;
    private String errorMessage = "Login failed";

    public boolean isLoginSuccessful() {
        return loginSuccessful;
    }

    public void setLoginSuccessful(boolean loginSuccessful) {
        this.loginSuccessful = loginSuccessful;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}