package com.example.ubermobileapp.models.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenericList<T> {

    @SerializedName("totalCount")
    @Expose
    private int totalCount;
    @SerializedName("results")
    @Expose
    private List<T> results;

    /**
     * No args constructor for use in serialization
     *
     */
    public GenericList() {
    }

    /**
     *
     * @param totalCount
     * @param results
     */
    public GenericList(int totalCount, List<T> results) {
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

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
