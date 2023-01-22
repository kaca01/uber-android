package com.example.ubermobileapp.models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Message implements Serializable {
    private String text;
    private String sender;
    private String receiver;
    private String time;
    private MessageType type;

    public Message() {};

    public Message(String text, String sender, String receiver, String time, MessageType type) {
        this.text = text;
        this.sender = sender;
        this.receiver = receiver;
        this.time = time;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getTime() {
        return time;
    }

    public MessageType getType() {
        return type;
    }


    public void setText(String text) {
        this.text = text;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Message{" +
                "text='" + text + '\'' +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", time=" + time +
                ", type=" + type +
                '}';
    }

    public static Message generateSupportMessage(String text){
        Message message = new Message();
        message.setText(text);
        message.setSender("korisnik1");
        message.setReceiver("Administrator Prezime");
        message.setTime(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
        message.setType(MessageType.SUPPORT);
        return message;
    }

    public static Message generateDriveMessage(String text, Drive drive) {
        Message message = new Message();
        message.setText(text);
        message.setSender("korisnik1");
        message.setReceiver(drive.getDriverName());
        message.setTime(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
        message.setType(MessageType.DRIVE);
        return message;
    }
}
