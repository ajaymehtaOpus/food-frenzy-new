package com.example.demo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void defaultConstructorAndSettersAndGettersShouldWork() {
        User user = new User();

        assertNull(user.getUemail());
        assertNull(user.getUpassword());
        assertNull(user.getUname());
        assertNull(user.getUnumber());
        assertEquals(0, user.getU_id());

        List<Orders> orders = new ArrayList<>();
        user.setU_id(42);
        user.setUname("Alice");
        user.setUemail("alice@example.com");
        user.setUpassword("secret");
        user.setUnumber(1234567890L);
        user.setOrders(orders);

        assertEquals(42, user.getU_id());
        assertEquals("Alice", user.getUname());
        assertEquals("alice@example.com", user.getUemail());
        assertEquals("secret", user.getUpassword());
        assertEquals(1234567890L, user.getUnumber());
        assertEquals(orders, user.getOrders());
    }

    @Test
    void constructorWithEmailAndPasswordShouldSetFields() {
        User user = new User("bob@example.com", "pwd123");

        assertEquals("bob@example.com", user.getUemail());
        assertEquals("pwd123", user.getUpassword());
        assertNull(user.getUname());
        assertNull(user.getUnumber());
        assertEquals(0, user.getU_id());
    }

    @Test
    void toStringShouldIncludeAllFields() {
        User user = new User();
        user.setU_id(7);
        user.setUname("Charlie");
        user.setUemail("charlie@example.com");
        user.setUpassword("pass");
        user.setUnumber(555L);
        List<Orders> orders = new ArrayList<>();
        user.setOrders(orders);

        String expected = "User [u_id=7, uname=Charlie, uemail=charlie@example.com, upassword=pass, unumber=555, orders="
                + orders + "]";

        assertEquals(expected, user.toString());
    }
}