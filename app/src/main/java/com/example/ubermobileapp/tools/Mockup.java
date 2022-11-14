package com.example.ubermobileapp.tools;

import com.example.ubermobileapp.adapters.PassengerAdapter;
import com.example.ubermobileapp.model.Drive;
import com.example.ubermobileapp.model.Message;
import com.example.ubermobileapp.model.MessageType;
import com.example.ubermobileapp.model.passenger.CreditCard;
import com.example.ubermobileapp.model.passenger.CreditCardType;
import com.example.ubermobileapp.model.passenger.Passenger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Mockup {

    // all messages for one user
    public static List<Message> getMessages(){
        ArrayList<Message> messages = new ArrayList<Message>();
        Message m1 = new Message("Lorem ipsum dolor sit amet, consectetur adipiscing elit..", "korisnik1", "Boban Bobo", "10:30", MessageType.DRIVE);
        Message m2 = new Message("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", "korisnik1", "Jelena Karleuša", "10:40", MessageType.DRIVE);
        Message m3 = new Message("Lorem ipsum dolor sit amet, consectetur adipiscing elit.", "korisnik1", "Dragoslav Simic", "10:50", MessageType.DRIVE);
        Message m4 = new Message("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", "Jelena Karleuša", "korisnik1", "11:30", MessageType.DRIVE);
        Message m5 = new Message("Lorem ipsum dolor sit amet, consectetur adipiscing elit..", "Boban Bobo", "korisnik1", "11:40", MessageType.PANIC);
        Message m6 = new Message("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", "Dragoslav Simic", "korisnik1", "11:50", MessageType.DRIVE);

        Message m7 = new Message("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", "korisnik1", "Administrator Prezime", "11:45", MessageType.SUPPORT);
        Message m8 = new Message("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", "Administrator Prezime", "korisnik1", "11:50", MessageType.SUPPORT);

        messages.add(m1);
        messages.add(m2);
        messages.add(m3);
        messages.add(m4);
        messages.add(m5);
        messages.add(m6);
        messages.add(m7);
        messages.add(m8);

        return messages;
    }

    public static List<Drive> getDrives(){
        ArrayList<Drive> drives = new ArrayList<Drive>();

        Drive d1 = new Drive("07/11/22", "Nikole Pašića 25", getDriveMessages(), "Boban Bobo");
        Drive d2 = new Drive("15/11/22", "Bulevar Despota Stefana 5A", getDriveMessages(), "Jelena Karleuša");
        Drive d3 = new Drive("10/11/22", "Bulevar Oslobodjenja", getDriveMessages(), "Dragoslav Simic");
        Drive d4 = new Drive("20/10/22", "Nikole Pašića 25", getNonSupportMessages(), "Boban Bobo");
        Drive d5 = new Drive("15/10/22", "Bulevar Despota Stefana 5A", getDriveMessages(), "Jelena Karleuša");
        Drive d6 = new Drive("10/10/22", "Bulevar Oslobodjenja", getDriveMessages(), "Dragoslav Simic");
        Drive d7 = new Drive("20/09/22", "Nikole Pašića 25", getDriveMessages(), "Boban Bobo");
        Drive d8 = new Drive("15/09/22", "Bulevar Despota Stefana 5A", getNonSupportMessages(), "Jelena Karleuša");
        Drive d9 = new Drive("10/09/22", "Bulevar Oslobodjenja", getDriveMessages(), "Dragoslav Simic");

        drives.add(d1);
        drives.add(d2);
        drives.add(d3);
        drives.add(d4);
        drives.add(d5);
        drives.add(d6);
        drives.add(d7);
        drives.add(d8);
        drives.add(d9);

        return drives;
    }

    public static ArrayList<Passenger> getPassengers() {
        ArrayList<Passenger> passengers = new ArrayList<Passenger>();

        Passenger p1 = new Passenger("Pera", "Peric", "062444555",
                "Bulevar despota Stefana", "pera@gmail.com", "password",
                "111-222-333");

        Passenger p2 = new Passenger("Mika", "Mikic", "062444555",
                "Bulevar despota Stefana", "mika@gmail.com", "password",
                "333-222-333");

        passengers.add(p1);
        passengers.add(p2);

        return passengers;
    }

    public static ArrayList<CreditCard> getCreditCards() {
        ArrayList<CreditCard> creditCards = new ArrayList<CreditCard>();
        CreditCard c1 = new CreditCard(CreditCardType.MASTERCARD, "111-222-333",
                                        20000.00);
        CreditCard c2 = new CreditCard(CreditCardType.VISA, "333-222-333",
                                35250.25);
        creditCards.add(c1);
        creditCards.add(c2);
        return creditCards;
    }

    private static ArrayList<Message> getNonSupportMessages(){
        ArrayList<Message> msgs = new ArrayList<>();

        for (Message msg : getMessages()) {
            if (msg.getType().equals(MessageType.DRIVE) || msg.getType().equals(MessageType.PANIC))
                msgs.add(msg);
        }
        return msgs;
    }

    private static ArrayList<Message> getDriveMessages(){
        ArrayList<Message> msgs = new ArrayList<>();

        for (Message msg : getMessages()) {
            if (msg.getType().equals(MessageType.DRIVE))
                msgs.add(msg);
        }
        return msgs;
    }

    public static ArrayList<Message> getSupportMessages(){
        ArrayList<Message> msgs = new ArrayList<>();

        for (Message msg : getMessages()) {
            if (msg.getType().equals(MessageType.SUPPORT))
                msgs.add(msg);
        }
        return msgs;
    }
}
