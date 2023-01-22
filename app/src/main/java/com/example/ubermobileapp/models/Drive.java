package com.example.ubermobileapp.models;

import com.example.ubermobileapp.model.enumeration.MessageType;

import java.io.Serializable;
import java.util.ArrayList;

public class Drive implements Serializable {
    private String startDate;
    private String destination;
    private ArrayList<Message> messages;
    private String driverName;

    public Drive() {};

    public Drive(String startDate, String destination, ArrayList<Message> messages, String driverName) {
        this.startDate = startDate;
        this.destination = destination;
        this.messages = messages;
        this.driverName = driverName;
    }

    // checks whether panic button has been pressed for this ride
    public Message getPanicMessage() {
        for (Message msg : messages){
            if (msg.getType().equals(MessageType.PANIC)) return msg;
        }
        return null;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    @Override
    public String toString() {
        return "Drive{" +
                "startDate=" + startDate +
                ", destination='" + destination + '\'' +
                ", messages=" + messages +
                ", driverName='" + driverName + '\'' +
                '}';
    }
}
