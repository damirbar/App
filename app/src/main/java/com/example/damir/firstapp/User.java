package com.example.damir.firstapp;

/**
 * Created by damir on 8/27/2017.
 */

public class User {

    private String name;
    private String password;
    private String email;

    public User(String name, String password, String email) {
        this.name     = name;
        this.password = password;
        this.email    = email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return "" + password.hashCode();
    }

    public String getEmail() {
        return email;
    }

}
