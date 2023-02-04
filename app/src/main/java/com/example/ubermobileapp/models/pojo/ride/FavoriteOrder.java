package com.example.ubermobileapp.models.pojo.ride;

import com.example.ubermobileapp.enumerations.VehicleTypeName;
import com.example.ubermobileapp.fragments.home.map.MapMainFragment;
import com.example.ubermobileapp.models.RideOrder;
import com.example.ubermobileapp.models.enumeration.VehicleType;
import com.example.ubermobileapp.models.pojo.user.User;
import com.example.ubermobileapp.services.implementation.PassengerService;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.net.UnknownServiceException;
import java.util.ArrayList;
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
    private String vehicleType;

    @SerializedName("passenger")
    @Expose
    private User passenger;

    @SerializedName("passengers")
    @Expose
    private List<User> passengers;

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

    public FavoriteOrder(Long id, String favoriteName, String vehicleType, User passenger,
                         List<User> passengers, boolean babyTransport, boolean petTransport,
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

    // request
    public FavoriteOrder(String favoriteName, String vehicleType,
                         List<User> passengers, boolean babyTransport, boolean petTransport,
                         List<Route> locations, User user) {
        this.favoriteName = favoriteName;
        this.vehicleType = vehicleType;
        this.passengers = passengers;
        this.passenger = user;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
        this.locations = locations;
    }

    public FavoriteOrder(RideOrder order, User passenger) {
        this.favoriteName = order.getFavoriteName();
        this.babyTransport = order.isBabyTransport();
        this.petTransport = order.isPetTransport();
        this.passengers = new ArrayList<>();
        this.passenger = passenger;
        this.vehicleType = VehicleType.fromInteger(order.getVehicleType()).toString();
        Route route = new Route(new Location(order.getDeparture(), MapMainFragment.departureLatitude, MapMainFragment.departureLongitude),
                new Location(order.getDestination(), MapMainFragment.departureLatitude, MapMainFragment.departureLongitude));
        List<Route> locations = new ArrayList<>();
        locations.add(route);
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

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public User getPassenger() {
        return passenger;
    }

    public void setPassenger(User passenger) {
        this.passenger = passenger;
    }

    public List<User> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<User> passengers) {
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

    @Override
    public String toString() {
        return "FavoriteOrder{" +
                "id=" + id +
                ", favoriteName='" + favoriteName + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", passenger=" + passenger +
                ", passengers=" + passengers +
                ", babyTransport=" + babyTransport +
                ", petTransport=" + petTransport +
                ", locations=" + locations +
                '}';
    }
}
