package com.kacper.kornas.model;

public class PaymentMethod {
    public String id;
    public double discount;
    public double limit;

    public PaymentMethod(String id, double discount, double limit) {
        this.id = id;
        this.discount = discount;
        this.limit = limit;
    }
}