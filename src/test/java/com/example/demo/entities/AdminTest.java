package com.example.demo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AdminTest {

    @Test
    void shouldSetAndGetAllFields() {
        Admin admin = new Admin();

        admin.setAdminId(42);
        admin.setAdminName("Alice");
        admin.setAdminEmail("alice@example.com");
        admin.setAdminPassword("secret");
        admin.setAdminNumber("1234567890");

        assertEquals(42, admin.getAdminId());
        assertEquals("Alice", admin.getAdminName());
        assertEquals("alice@example.com", admin.getAdminEmail());
        assertEquals("secret", admin.getAdminPassword());
        assertEquals("1234567890", admin.getAdminNumber());
    }

    @Test
    void shouldFormatToStringWithAllFields() {
        Admin admin = new Admin();
        admin.setAdminId(7);
        admin.setAdminName("Bob");
        admin.setAdminEmail("bob@example.com");
        admin.setAdminPassword("pwd");
        admin.setAdminNumber("999");

        String result = admin.toString();

        assertEquals("Admin [adminId=7, adminName=Bob, adminEmail=bob@example.com, adminPassword=pwd, adminNumber=999]", result);
    }
}