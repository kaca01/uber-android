package com.example.ubermobileapp.model;

import java.util.ArrayList;

public class RideOrder {
    String destination;
    String departure;
    int vehicleType;
    ArrayList<String> emails;
    boolean petTransport;
    boolean babyTransport;
    String startTime;
    String favoriteName;

    public RideOrder(){
        emails = new ArrayList<>();
    }

    public RideOrder(String destination, String departure, int vehicleType, ArrayList<String> emails,
                     boolean petTransport, boolean babyTransport, String startTime, String favoriteName) {
        this.destination = destination;
        this.departure = departure;
        this.vehicleType = vehicleType;
        this.emails = emails;
        this.petTransport = petTransport;
        this.babyTransport = babyTransport;
        this.startTime = startTime;
        this.favoriteName = favoriteName;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public int getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(int vehicleType) {
        this.vehicleType = vehicleType;
    }

    public ArrayList<String> getEmails() {
        return emails;
    }

    public void setEmails(ArrayList<String> emails) {
        this.emails = emails;
    }

    public boolean isPetTransport() {
        return petTransport;
    }

    public void setPetTransport(boolean petTransport) {
        this.petTransport = petTransport;
    }

    public boolean isBabyTransport() {
        return babyTransport;
    }

    public void setBabyTransport(boolean babyTransport) {
        this.babyTransport = babyTransport;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFavoriteName() {
        return favoriteName;
    }

    public void setFavoriteName(String favoriteName) {
        this.favoriteName = favoriteName;
    }
}
