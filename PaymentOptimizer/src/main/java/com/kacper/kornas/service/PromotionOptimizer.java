package com.kacper.kornas.service;

import com.kacper.kornas.model.Order;
import com.kacper.kornas.model.PaymentMethod;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PromotionOptimizer {
    public Map<String, Double> optimize(List<Order> orders, List<PaymentMethod> methods) {
        Map<String, Double> usage = new LinkedHashMap<>();
        for (PaymentMethod m : methods) {
            usage.put(m.id, 0.0);
        }
        for (Order o : orders) {
            String bestId = null;
            double bestDisc = 0;
            for (PaymentMethod m : methods) {
                if (m.limit < o.value) continue;
                double disc = 0;
                if (m.id.equals("PUNKTY")) {
                    disc = o.value * m.discount / 100;
                } else if (o.promotions.contains(m.id)) {
                    disc = o.value * m.discount / 100;
                }
                if (disc > bestDisc) {
                    bestDisc = disc;
                    bestId = m.id;
                }
            }
            if (bestId != null) {
                for (PaymentMethod m : methods) {
                    if (m.id.equals(bestId)) {
                        m.limit -= o.value;
                        usage.put(m.id, usage.get(m.id) + o.value);
                    }
                }
            }
        }
        return usage;
    }
}