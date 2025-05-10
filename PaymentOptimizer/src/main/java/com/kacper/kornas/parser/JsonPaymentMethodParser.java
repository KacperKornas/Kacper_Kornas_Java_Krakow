package com.kacper.kornas.parser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kacper.kornas.model.PaymentMethod;
import com.kacper.kornas.model.PaymentMethodRaw;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class JsonPaymentMethodParser {
    public static List<PaymentMethod> parse(String filename) {
        try (InputStream in = JsonPaymentMethodParser.class
                .getClassLoader()
                .getResourceAsStream("data/" + filename);
             InputStreamReader reader = new InputStreamReader(in)) {

            if (in == null) {
                throw new RuntimeException("Resource not found: data/" + filename);
            }

            Gson gson = new Gson();
            List<PaymentMethodRaw> raws = gson.fromJson(reader, new TypeToken<List<PaymentMethodRaw>>() {}.getType());
            List<PaymentMethod> methods = new ArrayList<>();
            for (PaymentMethodRaw raw : raws) {
                methods.add(new PaymentMethod(
                        raw.id,
                        Double.parseDouble(raw.discount),
                        Double.parseDouble(raw.limit)
                ));
            }
            return methods;

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse payment methods file", e);
        }
    }
}