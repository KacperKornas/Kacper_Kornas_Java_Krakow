package com.kacper.kornas.service;

import com.kacper.kornas.model.Order;
import com.kacper.kornas.model.PaymentMethod;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PromotionOptimizerTest {
    @Test
    void optimize_shouldUseBestPromotions() {
        Order o1 = new Order("O1", 100, List.of("M1"));
        PaymentMethod m1 = new PaymentMethod("M1", 10, 100);
        PaymentMethod p = new PaymentMethod("PUNKTY", 15, 100);

        PromotionOptimizer opt = new PromotionOptimizer();
        Map<String, Double> usage = opt.optimize(List.of(o1), List.of(m1, p));

        assertEquals(100.0, usage.get("PUNKTY"), 0.01);
    }
}