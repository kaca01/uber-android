package com.example.ubermobileapp.models.pojo.communication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rejection {
    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("timeOfRejection")
    @Expose
    private String timeOfRejection;

    /**
     * No args constructor for use in serialization
     *
     */
    public Rejection() {
    }

    /**
     *
     * @param reason
     * @param timeOfRejection
     */
    public Rejection(String reason, String timeOfRejection) {
        super();
        this.reason = reason;
        this.timeOfRejection = timeOfRejection;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTimeOfRejection() {
        return timeOfRejection;
    }

    public void setTimeOfRejection(String timeOfRejection) {
        this.timeOfRejection = timeOfRejection;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Rejection.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("reason");
        sb.append('=');
        sb.append(((this.reason == null)?"<null>":this.reason));
        sb.append(',');
        sb.append("timeOfRejection");
        sb.append('=');
        sb.append(((this.timeOfRejection == null)?"<null>":this.timeOfRejection));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }
}
