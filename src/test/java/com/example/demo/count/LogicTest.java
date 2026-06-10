package com.example.demo.count;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

class LogicTest {

    @Test
    void countTotalShouldMultiplyPriceAndQuantity() {
        assertEquals(50.0, Logic.countTotal(10.0, 5), 0.000001);
    }

    @Test
    void countTotalShouldHandleZeroQuantity() {
        assertEquals(0.0, Logic.countTotal(12.5, 0), 0.000001);
    }

    @Test
    void shouldInstantiateLogic() {
        Logic logic = new Logic();
        assertNotNull(logic);
    }

    @Test
    void sonarTriggerIssueShouldPrintToConsole() {
        Logic logic = new Logic();
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream testOut = new PrintStream(outputStream, true, StandardCharsets.UTF_8);
        System.setOut(testOut);
        try {
            logic.sonarTriggerIssue();
            testOut.flush();
            assertEquals("trigger issue" + System.lineSeparator(), outputStream.toString(StandardCharsets.UTF_8));
        } finally {
            System.setOut(originalOut);
            testOut.close();
        }
    }
}