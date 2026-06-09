package com.example.demo.controllers;

import com.example.demo.entities.User;
import com.example.demo.services.UserServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserServices services;

    @InjectMocks
    private UserController controller;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void addUserShouldDelegateToServiceAndRedirect() {
        String viewName = controller.addUser(user);

        assertEquals("redirect:/admin/services", viewName);
        verify(services).addUser(eq(user));
    }

    @Test
    void updateUserShouldDelegateToServiceAndRedirect() {
        String viewName = controller.updateUser(user, 42);

        assertEquals("redirect:/admin/services", viewName);
        verify(services).updateUser(eq(user), eq(42));
    }

    @Test
    void deleteUserShouldDelegateToServiceAndRedirect() {
        String viewName = controller.deleteUser(7);

        assertEquals("redirect:/admin/services", viewName);
        verify(services).deleteUser(eq(7));
    }
}