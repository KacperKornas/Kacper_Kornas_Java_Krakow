package com.kacper.kornas;

import com.google.gson.Gson;
import java.io.FileReader;
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

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Usage: java com.kacper.kornas.Main orders.json paymentmethods.json");
            return;
        }

        String ordersPath = "data/" + args[0];
        String methodsPath = "data/" + args[1];

        Gson gson = new Gson();
        OrderRaw[] rawOrders = gson.fromJson(new FileReader(ordersPath), OrderRaw[].class);
        PaymentMethodRaw[] rawMethods = gson.fromJson(new FileReader(methodsPath), PaymentMethodRaw[].class);
    }
}
