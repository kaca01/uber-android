package com.example.ubermobileapp.model;

import com.example.ubermobileapp.model.communication.Review;

import java.util.ArrayList;

public class Ride {
    private String startTime;
    private String endTime;
    private String date;
    private double cost;
    private int passengerNum;
    private double distance;
    private int path;
    private ArrayList<Review> reviews;

    public Ride() { }

    public Ride(String startTime, String endTime, String date, double cost, int passengerNum,
                double distance, int path, ArrayList<Review> reviews) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.cost = cost;
        this.passengerNum = passengerNum;
        this.distance = distance;
        this.path = path;
        this.reviews = reviews;
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

    public int getPassengerNum() {
        return passengerNum;
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

    public void setPassengerNum(int passengerNum) {
        this.passengerNum = passengerNum;
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

    @Override
    public String toString() {
        return "Ride{" +
                "startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", date='" + date + '\'' +
                ", cost=" + cost +
                ", passengerNum=" + passengerNum +
                ", distance=" + distance +
                ", path=" + path +
                ", reviews=" + reviews +
                '}';
    }
}
