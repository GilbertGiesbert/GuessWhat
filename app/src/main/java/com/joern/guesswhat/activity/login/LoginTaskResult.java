package com.joern.guesswhat.activity.login;

import com.joern.guesswhat.model.User;

/**
 * Created by joern on 24.04.2015.
 */
public class LoginTaskResult {

    private LoginTask.TaskType type;
    private boolean isSuccessful;
    private User user;
    private String errorMessage;

    public LoginTaskResult(LoginTask.TaskType type) {
        this.type = type;
        this.isSuccessful = false;
        this.user = null;
        this.errorMessage = "Internal error";
    }

    public LoginTask.TaskType getType() {
        return type;
    }

    public void setType(LoginTask.TaskType type) {
        this.type = type;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setIsSuccessful(boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
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