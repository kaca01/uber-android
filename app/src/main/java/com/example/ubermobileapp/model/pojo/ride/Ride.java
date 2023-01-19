package com.example.ubermobileapp.model.pojo.ride;

import com.example.ubermobileapp.fragments.home.map.MapMainFragment;
import com.example.ubermobileapp.model.RideOrder;
import com.example.ubermobileapp.model.enumeration.VehicleType;
import com.example.ubermobileapp.model.login.User;
import com.example.ubermobileapp.model.pojo.communication.Rejection;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class Ride {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("startTime")
    @Expose
    private String startTime;
    @SerializedName("endTime")
    @Expose
    private String endTime;
    @SerializedName("totalCost")
    @Expose
    private double totalCost;
    @SerializedName("driver")
    @Expose
    private User driver;
    @SerializedName("passengers")
    @Expose
    private List<User> passengers = null;
    @SerializedName("estimatedTimeInMinutes")
    @Expose
    private double estimatedTimeInMinutes;
    @SerializedName("vehicleType")
    @Expose
    private String vehicleType;
    @SerializedName("babyTransport")
    @Expose
    private boolean babyTransport;
    @SerializedName("petTransport")
    @Expose
    private boolean petTransport;
    @SerializedName("rejection")
    @Expose
    private Rejection rejection;
    @SerializedName("locations")
    @Expose
    private List<Route> locations = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("scheduledTime")
    @Expose
    private String scheduledTime;

    /**
     * No args constructor for use in serialization
     *
     */
    public Ride() {
    }

    public Ride(RideOrder order) {

        this.scheduledTime = order.getScheduledTime();
        this.babyTransport = order.isBabyTransport();
        this.petTransport = order.isPetTransport();
        this.vehicleType = VehicleType.fromInteger(order.getVehicleType()).toString();
        Route route = new Route();
        route.setDeparture(new Location(order.getDeparture(), MapMainFragment.departureLatitude, MapMainFragment.departureLongitude));
        route.setDestination(new Location(order.getDestination(), MapMainFragment.destinationLatitude, MapMainFragment.destinationLongitude));
        List<Route> locations = new ArrayList<>();
        locations.add(route);
        this.setLocations(locations);
        //todo provjeriti validan unos passenger-a
        List<User> passengers = new ArrayList<>();
        for (String e: order.getEmails()){
            User p = new User(e);
            passengers.add(p);
        }
        this.setPassengers(passengers);
    }
    /**
     *
     * @param passengers
     * @param babyTransport
     * @param scheduledTime
     * @param petTransport
     * @param rejection
     * @param estimatedTimeInMinutes
     * @param driver
     * @param startTime
     * @param locations
     * @param id
     * @param endTime
     * @param totalCost
     * @param vehicleType
     * @param status
     */
    public Ride(Long id, String startTime, String endTime, double totalCost, User driver, List<User> passengers, double estimatedTimeInMinutes, String vehicleType, boolean babyTransport, boolean petTransport, Rejection rejection, List<Route> locations, String status, String scheduledTime) {
        super();
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalCost = totalCost;
        this.driver = driver;
        this.passengers = passengers;
        this.estimatedTimeInMinutes = estimatedTimeInMinutes;
        this.vehicleType = vehicleType;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
        this.rejection = rejection;
        this.locations = locations;
        this.status = status;
        this.scheduledTime = scheduledTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public User getDriver() {
        return driver;
    }

    public void setDriver(User driver) {
        this.driver = driver;
    }

    public List<User> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<User> passengers) {
        this.passengers = passengers;
    }

    public double getEstimatedTimeInMinutes() {
        return estimatedTimeInMinutes;
    }

    public void setEstimatedTimeInMinutes(double estimatedTimeInMinutes) {
        this.estimatedTimeInMinutes = estimatedTimeInMinutes;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public boolean isBabyTransport() {
        return babyTransport;
    }

    public void setBabyTransport(boolean babyTransport) {
        this.babyTransport = babyTransport;
    }

    public boolean isPetTransport() {
        return petTransport;
    }

    public void setPetTransport(boolean petTransport) {
        this.petTransport = petTransport;
    }

    public Rejection getRejection() {
        return rejection;
    }

    public void setRejection(Rejection rejection) {
        this.rejection = rejection;
    }

    public List<Route> getLocations() {
        return locations;
    }

    public void setLocations(List<Route> locations) {
        this.locations = locations;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Ride.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(this.id);
        sb.append(',');
        sb.append("startTime");
        sb.append('=');
        sb.append(((this.startTime == null)?"<null>":this.startTime));
        sb.append(',');
        sb.append("endTime");
        sb.append('=');
        sb.append(((this.endTime == null)?"<null>":this.endTime));
        sb.append(',');
        sb.append("totalCost");
        sb.append('=');
        sb.append(this.totalCost);
        sb.append(',');
        sb.append("driver");
        sb.append('=');
        sb.append(((this.driver == null)?"<null>":this.driver));
        sb.append(',');
        sb.append("passengers");
        sb.append('=');
        sb.append(((this.passengers == null)?"<null>":this.passengers));
        sb.append(',');
        sb.append("estimatedTimeInMinutes");
        sb.append('=');
        sb.append(this.estimatedTimeInMinutes);
        sb.append(',');
        sb.append("vehicleType");
        sb.append('=');
        sb.append(((this.vehicleType == null)?"<null>":this.vehicleType));
        sb.append(',');
        sb.append("babyTransport");
        sb.append('=');
        sb.append(this.babyTransport);
        sb.append(',');
        sb.append("petTransport");
        sb.append('=');
        sb.append(this.petTransport);
        sb.append(',');
        sb.append("rejection");
        sb.append('=');
        sb.append(((this.rejection == null)?"<null>":this.rejection));
        sb.append(',');
        sb.append("locations");
        sb.append('=');
        sb.append(((this.locations == null)?"<null>":this.locations));
        sb.append(',');
        sb.append("status");
        sb.append('=');
        sb.append(((this.status == null)?"<null>":this.status));
        sb.append(',');
        sb.append("scheduledTime");
        sb.append('=');
        sb.append(((this.scheduledTime == null)?"<null>":this.scheduledTime));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
