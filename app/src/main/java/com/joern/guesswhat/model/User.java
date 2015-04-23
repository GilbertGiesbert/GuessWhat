package com.joern.guesswhat.model;

/**
 * Created by joern on 13.04.2015.
 */
public class User {

    private int id;
    private boolean registered;
    private String name;
    private String email;
    private int passwordHash;

    public User(int id, boolean registered, String name, String email, int passwordHash) {
        this.id = id;
        this.registered = registered;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(int passwordHash) {
        this.passwordHash = passwordHash;
    }
}