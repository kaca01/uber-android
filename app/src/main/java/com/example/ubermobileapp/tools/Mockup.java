package com.example.ubermobileapp.tools;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.model.Drive;
import com.example.ubermobileapp.model.Message;
import com.example.ubermobileapp.model.MessageType;
import com.example.ubermobileapp.model.Ride;
import com.example.ubermobileapp.model.Report;
import com.example.ubermobileapp.model.ReportsType;
import com.example.ubermobileapp.model.communication.Review;
import com.example.ubermobileapp.model.enumeration.ReviewType;
import com.example.ubermobileapp.model.passenger.CreditCard;
import com.example.ubermobileapp.model.passenger.CreditCardType;
import com.example.ubermobileapp.model.passenger.Passenger;
import com.example.ubermobileapp.model.enumeration.RideStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Mockup {

    public List<Ride> rides = new ArrayList<>();

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
        Message m8 = new Message("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", "Administrator Prezime", "korisnik1", "12:50", MessageType.SUPPORT);

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

        Passenger p1 = new Passenger("Pera", "Peric", "+381444123",
                "Bulevar despota Stefana", "pera@gmail.com", "123",
                "111-222-333");

//        Passenger p2 = new Passenger("Sima", "Simic", "+38111123", "Sumadijska",
//                "sima@gmail.com", "123", "222-333-999");

        Passenger p3 = new Passenger("Ana", "Pajić", "+381124343", "Backa 41",
                "ana@gmail.com", "123", "000-888-777");

        passengers.add(p1);
//        passengers.add(p2);
        passengers.add(p3);

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

    public static ArrayList<Report> getPassengerReports() {
        ArrayList<Report> reports = new ArrayList<Report>();
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now();
        Report r1 = new Report(1, ReportsType.NUMBER_OF_RIDES, 20, 2.8, 1,
                startDate, endDate);
        Report r2 = new Report(2, ReportsType.CROSSED_KM, 340.12, 12.77, 1,
                startDate, endDate);
        Report r3 = new Report(3, ReportsType.MONEY_SPENT, 12750.96, 1578.21,
                1, startDate, endDate);

        reports.add(r1);
        reports.add(r2);
        reports.add(r3);

        return reports;
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

    public static List<Ride> getRides() {
        ArrayList<Ride> rides = new ArrayList<>();
        ArrayList<Passenger> passengers = new ArrayList<>();
        passengers.add(getPassengers().get(getPassengers().size()-1));
//        passengers.add(getPassengers().get(0));

        Ride r1 = new Ride("14:01", "14:30", "10/02/2022", 800.00, getPassengers(), 10.00, R.drawable.map, getReviews(), RideStatus.FINISHED, "boki@gmail.com");
        Ride r2 = new Ride("10:00", "10:20", "11/05/2022", 562.36, getPassengers(), 5.03, R.drawable.map, null, RideStatus.FINISHED, "zlata@gmail.com");
        Ride r3 = new Ride("21:56", "22:24", "15/10/2022", 843.12, getPassengers(), 12.11, R.drawable.map, null, RideStatus.FINISHED, "duki@gmail.com");
        Ride r4 = new Ride("14:00", "14:30", "10/02/2022", 800.00, passengers, 10.00, R.drawable.map, null, RideStatus.ACCEPTED, "boki@gmail.com");

        rides.add(r2);
        rides.add(r1);
        rides.add(r3);
        rides.add(r4);

        return rides;
    }

    public static List<Ride> getFavoriteRides() {
        ArrayList<Ride> rides = new ArrayList<>();

        Ride r1 = new Ride("14:00", "14:30", "10/02/2022", 800.00, getPassengers(), 10.00, R.drawable.map, null, RideStatus.FINISHED, "boki@gmail.com");
        Ride r2 = new Ride("10:00", "10:20", "11/05/2022", 562.36, getPassengers(), 5.03, R.drawable.map, null, RideStatus.FINISHED, "duki@gmail.com");

        rides.add(r1);
        rides.add(r2);

        return rides;
    }

        public static CreditCard getCreditCard (String cardNumber){
            for (CreditCard card : getCreditCards()) {
                if (card.getCardNumber().equals(cardNumber.trim())) return card;
            }
            return null;

    }

    public static Report getReport(int id) {
        for (Report report : getPassengerReports()) {
            if (report.getID() == id) return report;
        }
        return null;
    }

    public static ArrayList<Review> getReviews() {
        ArrayList<Passenger> passengers = getPassengers();
        ArrayList<Review> reviews = new ArrayList<>();

        Review r1 = new Review(1, 5, "Great driver!", passengers.get(0),
                ReviewType.DRIVER);
        Review r2 = new Review(2, 4, "The vehicle could have been cleaner!", passengers.get(0),
                ReviewType.VEHICLE);

        reviews.add(r1);
        reviews.add(r2);

        return reviews;
    }

    public void setRides(List<Ride> newRides) {
        this.rides = newRides;
    }
}
