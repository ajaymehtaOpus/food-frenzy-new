package com.example.demo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Date;

import org.junit.jupiter.api.Test;

class OrdersTest {

    @Test
    void shouldSetAndGetAllFieldsAndFormatToString() {
        Orders orders = new Orders();
        Date orderDate = new Date(123456789L);
        User user = new User();

        orders.setoId(10);
        orders.setoName("Laptop");
        orders.setoPrice(999.99);
        orders.setoQuantity(2);
        orders.setOrderDate(orderDate);
        orders.setTotalAmmout(1999.98);
        orders.setUser(user);

        assertEquals(10, orders.getoId());
        assertEquals("Laptop", orders.getoName());
        assertEquals(999.99, orders.getoPrice());
        assertEquals(2, orders.getoQuantity());
        assertSame(orderDate, orders.getOrderDate());
        assertEquals(1999.98, orders.getTotalAmmout());
        assertSame(user, orders.getUser());
        assertEquals("Orders [oId=10, oName=Laptop, oPrice=999.99, oQuantity=2, orderDate=" + orderDate
                + ", totalAmmout=1999.98, user=" + user + "]", orders.toString());
    }

    @Test
    void shouldReturnDefaultValuesAndToStringForNewInstance() {
        Orders orders = new Orders();

        assertEquals(0, orders.getoId());
        assertEquals(0.0, orders.getoPrice());
        assertEquals(0, orders.getoQuantity());
        assertEquals(0.0, orders.getTotalAmmout());
        assertEquals("Orders [oId=0, oName=null, oPrice=0.0, oQuantity=0, orderDate=null, totalAmmout=0.0, user=null]",
                orders.toString());
    }
}