package com.example.demo.loginCredentials;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class AdminLoginTest {

    @Test
    void defaultConstructorAndSettersAndGettersShouldWork() {
        AdminLogin adminLogin = new AdminLogin();

        assertNotNull(adminLogin);
        assertEquals(null, adminLogin.getEmail());
        assertEquals(null, adminLogin.getPassword());

        adminLogin.setEmail("admin@example.com");
        adminLogin.setPassword("secret");

        assertEquals("admin@example.com", adminLogin.getEmail());
        assertEquals("secret", adminLogin.getPassword());
    }

    @Test
    void toStringShouldIncludeEmailAndPassword() {
        AdminLogin adminLogin = new AdminLogin();
        adminLogin.setEmail("admin@example.com");
        adminLogin.setPassword("secret");

        assertEquals("AdminLogin [name=admin@example.com, password=secret]", adminLogin.toString());
    }
}