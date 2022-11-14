package com.example.ubermobileapp.model.passenger;

import com.example.ubermobileapp.model.ReportsType;

public class Report {
    private int ID;
    private ReportsType type;
    private double total;
    private double average;
    private int passengerID;

    public Report() {
    }

    public Report(int ID, ReportsType type, double total, double average, int passengerID) {
        this.ID = ID;
        this.type = type;
        this.total = total;
        this.average = average;
        this.passengerID = passengerID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getPassengerID() {
        return passengerID;
    }

    public void setPassengerID(int passengerID) {
        this.passengerID = passengerID;
    }

    public ReportsType getType() {
        return type;
    }

    public void setType(ReportsType type) {
        this.type = type;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    @Override
    public String toString() {
        return "PassengerReport{" +
                "type=" + type +
                ", total=" + total +
                ", average=" + average +
                '}';
    }
}
