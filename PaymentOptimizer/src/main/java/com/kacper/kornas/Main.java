package com.kacper.kornas;

import com.google.gson.Gson;
import java.io.FileReader;
import java.util.*;
import java.io.InputStream;
import java.io.InputStreamReader;


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

        String ordersFile  = "data/" + args[0];
        String methodsFile = "data/" + args[1];

        Gson gson = new Gson();

        InputStream  ordersStream  = Main.class.getClassLoader().getResourceAsStream(ordersFile);
        InputStream  methodsStream = Main.class.getClassLoader().getResourceAsStream(methodsFile);
        if (ordersStream == null || methodsStream == null) {
            System.err.println("Cannot find resource: " + ordersFile + " or " + methodsFile);
            return;
        }

        OrderRaw[] rawOrders   = gson.fromJson(new InputStreamReader(ordersStream),   OrderRaw[].class);
        PaymentMethodRaw[] rawMethods = gson.fromJson(new InputStreamReader(methodsStream), PaymentMethodRaw[].class);

        Order[] orders = new Order[rawOrders.length];
        for (int i = 0; i < rawOrders.length; i++) {
            OrderRaw ro = rawOrders[i];
            Order o = new Order();
            o.id = ro.id;
            o.value = Double.parseDouble(ro.value);
            o.promotions = ro.promotions != null ? ro.promotions : new ArrayList<>();
            orders[i] = o;
        }

        PaymentMethod[] methods = new PaymentMethod[rawMethods.length];
        for (int i = 0; i < rawMethods.length; i++) {
            PaymentMethodRaw rm = rawMethods[i];
            PaymentMethod m = new PaymentMethod();
            m.id = rm.id;
            m.discount = Double.parseDouble(rm.discount);
            m.limit = Double.parseDouble(rm.limit);
            methods[i] = m;
        }

        Map<String, PaymentMethod> methodMap = new HashMap<>();
        for (PaymentMethod m : methods) {
            methodMap.put(m.id, m);
        }
        Map<String, Double> usage = new LinkedHashMap<>();
        for (PaymentMethod m : methods) {
            usage.put(m.id, 0.0);
        }

        for (Order o : orders) {
            String bestMethod = null;
            double bestDiscountAmount = 0;
            double useCard = 0;
            double usePoints = 0;
            double orderValue = o.value;

            for (PaymentMethod m : methods) {
                if (m.id.equals("PUNKTY")) continue;
                if (m.limit >= orderValue) {
                    double discPerc = o.promotions.contains(m.id) ? m.discount : 0;
                    double discAmount = orderValue * discPerc / 100;
                    if (discAmount > bestDiscountAmount) {
                        bestDiscountAmount = discAmount;
                        bestMethod = m.id;
                        useCard = orderValue;
                        usePoints = 0;
                    }
                }
            }

            PaymentMethod p = methodMap.get("PUNKTY");
            if (p != null && p.limit >= orderValue) {
                double discAmount = orderValue * p.discount / 100;
                if (discAmount > bestDiscountAmount) {
                    bestDiscountAmount = discAmount;
                    bestMethod = "PUNKTY";
                    useCard = 0;
                    usePoints = orderValue;
                }
            }

            if (p != null && p.limit >= orderValue * 0.1) {
                double discAmount = orderValue * 0.1;
                if (discAmount > bestDiscountAmount) {
                    bestDiscountAmount = discAmount;
                    bestMethod = "PUNKTY";
                    usePoints = orderValue * 0.1;
                    useCard = orderValue * 0.9;
                }
            }

            if (useCard > 0 && bestMethod != null && !bestMethod.equals("PUNKTY")) {
                PaymentMethod m = methodMap.get(bestMethod);
                m.limit -= useCard;
                usage.put(bestMethod, usage.get(bestMethod) + useCard);
            }
            if (usePoints > 0) {
                p.limit -= usePoints;
                usage.put("PUNKTY", usage.get("PUNKTY") + usePoints);
            }
        }

        for (Map.Entry<String, Double> e : usage.entrySet()) {
            if (e.getValue() > 0) {
                System.out.printf("%s %.2f%n", e.getKey(), e.getValue());
            }
        }
    }
}
