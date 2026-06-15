package com.example.demo.loginCredentials;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class UserLoginTest {

    @Test
    void shouldSetAndGetUserEmail() {
        UserLogin userLogin = new UserLogin();

        userLogin.setUserEmail("test@example.com");

        assertEquals("test@example.com", userLogin.getUserEmail());
    }

    @Test
    void shouldSetAndGetUserPassword() {
        UserLogin userLogin = new UserLogin();

        userLogin.setUserPassword("secret");

        assertEquals("secret", userLogin.getUserPassword());
    }

    @Test
    void shouldReturnFormattedToStringWithValues() {
        UserLogin userLogin = new UserLogin();
        userLogin.setUserEmail("test@example.com");
        userLogin.setUserPassword("secret");

        assertEquals("UserLogin [userEmail=test@example.com, userPassword=secret]", userLogin.toString());
    }

    @Test
    void shouldReturnFormattedToStringWithNullValuesByDefault() {
        UserLogin userLogin = new UserLogin();

        assertEquals("UserLogin [userEmail=null, userPassword=null]", userLogin.toString());
        assertNull(userLogin.getUserEmail());
        assertNull(userLogin.getUserPassword());
    }
}