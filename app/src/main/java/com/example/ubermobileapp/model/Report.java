package com.example.ubermobileapp.model;

import java.time.LocalDate;
import java.util.Date;

public class Report {
    private int ID;
    private ReportsType type;
    private double total;
    private double average;
    private int userID;
    private LocalDate startDate;
    private LocalDate endDate;

    public Report() {
    }

    public Report(int ID, ReportsType type, double total, double average, int passengerID,
                  LocalDate startDate, LocalDate endDate) {
        this.ID = ID;  // TODO: call here generateID, after implementation
        this.type = type;
        this.total = total;
        this.average = average;
        this.userID = passengerID;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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

    public LocalDate getStartDate() {return this.startDate;}

    public void setStartDate(LocalDate startDate) {this.startDate = startDate;}

    public LocalDate getEndDate() {return this.endDate;}

    public void setEndDate(LocalDate endDate) {this.endDate = endDate;}

    @Override
    public String toString() {
        return "Report{" +
                "ID=" + ID +
                ", type=" + type +
                ", total=" + total +
                ", average=" + average +
                ", userID=" + userID +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    private int generateID() {
        // TODO: implement this method
        return 1;
    }
}
