package com.example.ubermobileapp.models.pojo.communication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewList {

    @SerializedName("vehicleReview")
    @Expose
    private Review vehicleReview;
    @SerializedName("driverReview")
    @Expose
    private Review driverReview;

    public ReviewList() { }

    public ReviewList(Review vehicleReview, Review driverReview) {
        this.vehicleReview = vehicleReview;
        this.driverReview = driverReview;
    }

    public Review getVehicleReview() {
        return vehicleReview;
    }

    public void setVehicleReview(Review vehicleReview) {
        this.vehicleReview = vehicleReview;
    }

    public Review getDriverReview() {
        return driverReview;
    }

    public void setDriverReview(Review driverReview) {
        this.driverReview = driverReview;
    }

    @Override
    public String toString() {
        return "ReviewList{" +
                "vehicleReview=" + vehicleReview +
                ", driverReview=" + driverReview +
                '}';
    }
}
