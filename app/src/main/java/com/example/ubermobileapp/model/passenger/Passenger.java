package com.example.ubermobileapp.model.passenger;

public class Passenger {
    private int ID;
    private String name;
    private String surname;
    private String telephoneNumber;
    private String address;
    private String email;
    private String password;
    private String profilePicture;

    public Passenger() {
    }

    public Passenger(String name, String surname, String telephoneNumber, String address,
                     String email, String password, String profilePicture) {
        this.ID = generateID();
        this.name = name;
        this.surname = surname;
        this.telephoneNumber = telephoneNumber;
        this.address = address;
        this.email = email;
        this.password = password;
        this.profilePicture = profilePicture;
    }
    public int getID() {return ID;}

    public void setID(int id) {this.ID = id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "firstName='" + name + '\'' +
                ", lastName='" + surname + '\'' +
                ", number='" + telephoneNumber + '\'' +
                ", postalAddress='" + address + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", creditCardNumber='" + profilePicture + '\'' +
                '}';
    }

    private int generateID() {
        // TODO implement this method
        return 1;
    }
}
