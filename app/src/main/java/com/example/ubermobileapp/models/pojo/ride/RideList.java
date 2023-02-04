package com.example.ubermobileapp.models.pojo.ride;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RideList {
    @SerializedName("totalCount")
    @Expose
    private int totalCount;
    @SerializedName("results")
    @Expose
    private List<Ride> results = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public RideList() {
    }

    /**
     *
     * @param totalCount
     * @param results
     */
    public RideList(int totalCount, List<Ride> results) {
        super();
        this.totalCount = totalCount;
        this.results = results;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<Ride> getResults() {
        return results;
    }

    public void setResults(List<Ride> results) {
        this.results = results;
    }
}
