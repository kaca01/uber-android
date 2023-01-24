package com.example.ubermobileapp.models.pojo.communication;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewList implements Parcelable {

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

    protected ReviewList(Parcel in) {

    }

    public static final Creator<ReviewList> CREATOR = new Creator<ReviewList>() {
        @Override
        public ReviewList createFromParcel(Parcel in) {
            return new ReviewList(in);
        }

        @Override
        public ReviewList[] newArray(int size) {
            return new ReviewList[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if(vehicleReview != null) dest.writeString(vehicleReview.toString());
        if(driverReview != null) dest.writeString(driverReview.toString());
    }
}
