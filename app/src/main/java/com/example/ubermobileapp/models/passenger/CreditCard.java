package com.example.ubermobileapp.models.passenger;

public class CreditCard {
    private CreditCardType type;
    private String cardNumber;
    private double amount;

    public CreditCard() {
    }

    public CreditCard(CreditCardType type, String cardNumber, double amount) {
        this.type = type;
        this.cardNumber = cardNumber;
        this.amount = amount;
    }

    public CreditCardType getType() {
        return type;
    }

    public void setType(CreditCardType type) {
        this.type = type;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "type=" + type +
                ", cardNumber='" + cardNumber + '\'' +
                ", amount=" + amount +
                '}';
    }

    public double addCache(double cache) {
        return this.amount + cache;
    }
}
