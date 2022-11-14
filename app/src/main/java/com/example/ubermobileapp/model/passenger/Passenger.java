package com.example.ubermobileapp.model.passenger;

public class Passenger {
    private String firstName;
    private String lastName;
    private String number;
    private String postalAddress;
    private String email;
    private String password;

    public Passenger() {
    }

    public Passenger(String firstName, String lastName, String number, String postalAddress,
                     String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
        this.postalAddress = postalAddress;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", number='" + number + '\'' +
                ", postalAddress='" + postalAddress + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
