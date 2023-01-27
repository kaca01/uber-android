package com.example.ubermobileapp.models.pojo.communication;

import com.example.ubermobileapp.models.pojo.user.Passenger;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Review  {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("rating")
    @Expose
    private int rating;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("passenger")
    @Expose
    private Passenger passenger;
    @SerializedName("type")
    @Expose
    private String type;

    public Review() { }

    public Review(Long id, int rating, String comment, Passenger passenger, String type) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.passenger = passenger;
        this.type = type;
    }

    // request
    public Review(int rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", passenger=" + passenger +
                ", type='" + type + '\'' +
                '}';
    }
}
