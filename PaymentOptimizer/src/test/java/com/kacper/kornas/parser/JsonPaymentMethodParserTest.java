package com.kacper.kornas.parser;

import com.kacper.kornas.model.PaymentMethod;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonPaymentMethodParserTest {
    @Test
    void parse_shouldReturnMethods() {
        List<PaymentMethod> methods = JsonPaymentMethodParser.parse("paymentmethods.json");
        assertEquals(3, methods.size());
        assertTrue(methods.stream().anyMatch(m -> m.id.equals("PUNKTY")));
    }
}