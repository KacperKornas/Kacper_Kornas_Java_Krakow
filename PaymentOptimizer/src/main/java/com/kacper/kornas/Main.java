package com.kacper.kornas;

import com.kacper.kornas.model.Order;
import com.kacper.kornas.model.PaymentMethod;
import com.kacper.kornas.parser.JsonOrderParser;
import com.kacper.kornas.parser.JsonPaymentMethodParser;
import com.kacper.kornas.service.PromotionOptimizer;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java -jar payment-optimizer.jar <orders.json> <paymentmethods.json>");
            return;
        }
        List<Order> orders = JsonOrderParser.parse(args[0]);
        List<PaymentMethod> methods = JsonPaymentMethodParser.parse(args[1]);

        Map<String, Double> usage = new PromotionOptimizer().optimize(orders, methods);
        for (String id : usage.keySet()) {
            double amount = usage.get(id);
            if (amount > 0) {
                System.out.printf("%s %.2f", id, amount);
                System.out.println();
            }
        }
    }
}