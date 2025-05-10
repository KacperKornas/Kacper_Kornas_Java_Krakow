package com.kacper.kornas;

import java.util.*;

public class Main {

    static class OrderRaw {
        String id;
        String value;
        List<String> promotions;
    }
    static class PaymentMethodRaw {
        String id;
        String discount;
        String limit;
    }

    static class Order {
        String id;
        double value;
        List<String> promotions;
    }
    static class PaymentMethod {
        String id;
        double discount;
        double limit;
    }

    public static void main(String[] args) {

    }
}
