package com.example.ubermobileapp.model.communication;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.ubermobileapp.model.enumeration.ReviewType;
import com.example.ubermobileapp.model.passenger.Passenger;

public class Review implements Parcelable {
    private int id;
    private int rating;
    private String comment;
    private Passenger passenger;
    private ReviewType type;

    public Review(int id, int rating, String comment, Passenger passenger, ReviewType type) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.passenger = passenger;
        this.type = type;
    }

    protected Review(Parcel in) {
        id = in.readInt();
        rating = in.readInt();
        comment = in.readString();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public ReviewType getType() {
        return type;
    }

    public void setType(ReviewType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", passenger=" + passenger +
                ", type=" + type +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(rating);
        parcel.writeString(comment);
    }
}
