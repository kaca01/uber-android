package com.example.ubermobileapp.model;

import com.example.ubermobileapp.model.communication.Review;
import com.example.ubermobileapp.model.passenger.Passenger;
import com.example.ubermobileapp.model.enumeration.RideStatus;

import java.util.ArrayList;

public class Ride {
    private String startTime;
    private String endTime;
    private String date;
    private double cost;
    private ArrayList<Passenger> passengers;
    private double distance;
    private int path;
    private ArrayList<Review> reviews;
    private RideStatus status;
    private String driver;

    public Ride() { }

    public Ride(String startTime, String endTime, String date, double cost, ArrayList<Passenger> passengers,
                double distance, int path, ArrayList<Review> reviews, RideStatus status, String driver) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.cost = cost;
        this.passengers = passengers;
        this.distance = distance;
        this.path = path;
        this.reviews = reviews;
        this.status = status;
        this.driver = driver;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getDate() {
        return date;
    }

    public double getCost() {
        return cost;
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    public double getDistance() {
        return distance;
    }

    public int getPath() {
        return path;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setPassengers(ArrayList<Passenger> passengers) {
        this.passengers = passengers;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setPath(int path) {
        this.path = path;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    public RideStatus getStatus() {
        return status;
    }

    public void setStatus(RideStatus status) {
        this.status = status;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    @Override
    public String toString() {
        return "Ride{" +
                "startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", date='" + date + '\'' +
                ", cost=" + cost +
                ", passengers=" + passengers +
                ", distance=" + distance +
                ", path=" + path +
                ", reviews=" + reviews +
                ", status=" + status +
                ", driver='" + driver + '\'' +
                '}';
    }
}
