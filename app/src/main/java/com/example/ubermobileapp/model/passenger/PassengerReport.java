package com.example.ubermobileapp.model.passenger;

import com.example.ubermobileapp.model.ReportsType;

public class PassengerReport {
    private ReportsType type;
    private double total;
    private double average;

    public PassengerReport() {
    }

    public PassengerReport(ReportsType type, double total, double average) {
        this.type = type;
        this.total = total;
        this.average = average;
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
