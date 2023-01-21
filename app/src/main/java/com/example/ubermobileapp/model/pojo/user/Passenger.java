package com.example.ubermobileapp.model.pojo.user;

import com.example.ubermobileapp.model.pojo.user.User;

public class Passenger extends User {
    private String password;

    public Passenger() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Passenger(Long id, String email, String firstname, String lastname, String password) {
        super(id, email, firstname, lastname);
        this.password = password;
    }
}