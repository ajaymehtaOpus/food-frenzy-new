package com.example.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import com.example.demo.entities.Product;
import com.example.demo.services.ProductServices;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductServices productServices;

    @InjectMocks
    private ProductController productController;

    @Test
    void addProductShouldDelegateToServiceAndRedirect() {
        Product product = new Product();

        String viewName = productController.addProduct(product);

        assertEquals("redirect:/admin/services", viewName);
        verify(productServices).addProduct(product);
    }

    @Test
    void updateProductShouldDelegateToServiceAndRedirect() {
        Product product = new Product();
        int productId = 42;

        String viewName = productController.updateProduct(product, productId);

        assertEquals("redirect:/admin/services", viewName);
        verify(productServices).updateproduct(product, productId);
    }

    @Test
    void deleteShouldDelegateToServiceAndRedirect() {
        int productId = 7;

        String viewName = productController.delete(productId);

        assertEquals("redirect:/admin/services", viewName);
        verify(productServices).deleteProduct(productId);
    }
}