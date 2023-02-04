package com.example.ubermobileapp.models.pojo.user;

public class Passenger extends User {

    public Passenger() {
    }

    public Passenger(String name, String surname, String telephoneNumber, String email, String address, String password) {
        super(name, surname, telephoneNumber, email, address, password);
    }

    public Passenger(Long id, String name, String surname, String profilePicture, String telephoneNumber,
                     String email, String address) {
        super(id, name, surname, profilePicture, telephoneNumber, email, address);
    }
}