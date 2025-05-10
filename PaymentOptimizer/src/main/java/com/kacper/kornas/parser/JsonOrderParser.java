package com.kacper.kornas.parser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kacper.kornas.model.Order;
import com.kacper.kornas.model.OrderRaw;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class JsonOrderParser {
    public static List<Order> parse(String filename) {
        try (InputStream in = JsonOrderParser.class
                .getClassLoader()
                .getResourceAsStream("data/" + filename);
             InputStreamReader reader = new InputStreamReader(in)) {

            if (in == null) {
                throw new RuntimeException("Resource not found: data/" + filename);
            }

            Gson gson = new Gson();
            List<OrderRaw> raws = gson.fromJson(reader, new TypeToken<List<OrderRaw>>() {}.getType());
            List<Order> orders = new ArrayList<>();
            for (OrderRaw raw : raws) {
                orders.add(new Order(
                        raw.id,
                        Double.parseDouble(raw.value),
                        raw.promotions != null ? raw.promotions : List.of()
                ));
            }
            return orders;

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse orders file", e);
        }
    }
}