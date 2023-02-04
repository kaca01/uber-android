package com.example.ubermobileapp.models;

import java.util.ArrayList;

public class RideOrder {
    Long rideId;
    String destination;
    String departure;
    int vehicleType;
    ArrayList<String> emails;
    boolean petTransport;
    boolean babyTransport;
    String scheduledTime;
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
        this.scheduledTime = startTime;
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

    public String getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public String getFavoriteName() {
        return favoriteName;
    }

    public void setFavoriteName(String favoriteName) {
        this.favoriteName = favoriteName;
    }

    public Long getRideId() {
        return rideId;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
    }
}
