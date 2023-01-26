package com.example.ubermobileapp.models.pojo.ride;

import com.example.ubermobileapp.enumerations.VehicleTypeName;
import com.example.ubermobileapp.models.pojo.user.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.net.UnknownServiceException;
import java.util.List;
import java.util.Set;

public class FavoriteOrder {
    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("favoriteName")
    @Expose
    private String favoriteName;

    @SerializedName("vehicleType")
    @Expose
    private VehicleTypeName vehicleType;

    @SerializedName("passenger")
    @Expose
    private User passenger;

    @SerializedName("passengers")
    @Expose
    private Set<User> passengers;

    @SerializedName("babyTransport")
    @Expose
    private boolean babyTransport;

    @SerializedName("petTransport")
    @Expose
    private boolean petTransport;

    @SerializedName("locations")
    @Expose
    private List<Route> locations;

    public FavoriteOrder() {

    }

    public FavoriteOrder(Long id, String favoriteName, VehicleTypeName vehicleType, User passenger,
                         Set<User> passengers, boolean babyTransport, boolean petTransport,
                         List<Route> locations) {
        this.id = id;
        this.favoriteName = favoriteName;
        this.vehicleType = vehicleType;
        this.passenger = passenger;
        this.passengers = passengers;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
        this.locations = locations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFavoriteName() {
        return favoriteName;
    }

    public void setFavoriteName(String favoriteName) {
        this.favoriteName = favoriteName;
    }

    public VehicleTypeName getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleTypeName vehicleType) {
        this.vehicleType = vehicleType;
    }

    public User getPassenger() {
        return passenger;
    }

    public void setPassenger(User passenger) {
        this.passenger = passenger;
    }

    public Set<User> getPassengers() {
        return passengers;
    }

    public void setPassengers(Set<User> passengers) {
        this.passengers = passengers;
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

    public List<Route> getLocations() {
        return locations;
    }

    public void setLocations(List<Route> locations) {
        this.locations = locations;
    }
}
