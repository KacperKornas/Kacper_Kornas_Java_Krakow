package com.kacper.kornas.model;

import java.util.List;

public class Order {
    public String id;
    public double value;
    public List<String> promotions;

    public Order(String id, double value, List<String> promotions) {
        this.id = id;
        this.value = value;
        this.promotions = promotions;
    }
}