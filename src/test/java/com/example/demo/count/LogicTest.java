package com.example.demo.count;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class LogicTest {

    @Test
    void shouldInstantiateLogic() {
        Logic logic = new Logic();
        assertNotNull(logic);
    }

    @Test
    void countTotalShouldMultiplyPriceByQuantity() {
        double result = Logic.countTotal(12.5, 4);
        assertEquals(50.0, result, 0.0001);
    }

    @Test
    void applyDiscountShouldApplyTenPercentForPremiumCustomer() {
        double result = Logic.applyDiscount(100.0, true);
        assertEquals(90.0, result, 0.0001);
    }

    @Test
    void applyDiscountShouldReturnOriginalAmountForNonPremiumCustomer() {
        double result = Logic.applyDiscount(100.0, false);
        assertEquals(100.0, result, 0.0001);
    }

    @Test
    void sumShouldAddNinetyCentsForPremiumCustomer() {
        double result = Logic.Sum(10.0, true);
        assertEquals(10.9, result, 0.0001);
    }

    @Test
    void sumShouldReturnOriginalAmountForNonPremiumCustomer() {
        double result = Logic.Sum(10.0, false);
        assertEquals(10.0, result, 0.0001);
    }
}