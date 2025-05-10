package com.kacper.kornas.parser;

import com.kacper.kornas.model.Order;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonOrderParserTest {
    @Test
    void parse_shouldReturnOrders() {
        List<Order> orders = JsonOrderParser.parse("orders.json");
        assertEquals(4, orders.size());
        assertEquals("ORDER1", orders.get(0).id);
    }
}