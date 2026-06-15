package com.example.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import com.example.demo.entities.Product;
import com.example.demo.services.ProductServices;

@ExtendWith(MockitoExtension.class)
class HomeControllerTest {

    @Mock
    private ProductServices productServices;

    @Mock
    private Model model;

    @InjectMocks
    private HomeController homeController;

    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        product1 = new Product();
        product2 = new Product();
    }

    @Test
    void homeShouldReturnHomeView() {
        String viewName = homeController.home();

        assertEquals("Home", viewName);
    }

    @Test
    void productsShouldAddAllProductsToModelAndReturnProductsView() {
        List<Product> products = Arrays.asList(product1, product2);
        doReturn(products).when(productServices).getAllProducts();

        String viewName = homeController.products(model);

        assertEquals("Products", viewName);
        verify(productServices, times(1)).getAllProducts();
        verify(model, times(1)).addAttribute(eq("products"), eq(products));
    }

    @Test
    void locationShouldReturnLocateUsView() {
        String viewName = homeController.location();

        assertEquals("Locate_us", viewName);
    }

    @Test
    void aboutShouldReturnAboutView() {
        String viewName = homeController.about();

        assertEquals("About", viewName);
    }

    @Test
    void loginShouldAddAdminLoginToModelAndReturnLoginView() {
        String viewName = homeController.login(model);

        assertEquals("Login", viewName);
        verify(model, times(1)).addAttribute(eq("adminLogin"), any());
    }
}