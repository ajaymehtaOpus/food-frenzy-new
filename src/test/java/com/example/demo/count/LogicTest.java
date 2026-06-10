package com.example.demo.count;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class LogicTest {

    @Test
    void countTotalShouldMultiplyPriceByQuantity() {
        double result = Logic.countTotal(12.5, 4);

        assertEquals(50.0, result, 0.0000001);
    }

    @Test
    void countTotalShouldHandleZeroQuantity() {
        double result = Logic.countTotal(99.99, 0);

        assertEquals(0.0, result, 0.0000001);
    }

    @Test
    void shouldInstantiateLogicClass() {
        Logic logic = new Logic();

        assertNotNull(logic);
    }
}