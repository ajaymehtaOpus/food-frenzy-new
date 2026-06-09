package com.example.demo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.example.demo.entities.Product;
import com.example.demo.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServicesTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServices productServices;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
    }

    @Test
    void addProductShouldDelegateToRepositorySave() {
        productServices.addProduct(product);

        verify(productRepository, times(1)).save(eq(product));
    }

    @Test
    void getAllProductsShouldReturnRepositoryResults() {
        List<Product> expected = Arrays.asList(new Product(), new Product());
        when(productRepository.findAll()).thenReturn(expected);

        List<Product> actual = productServices.getAllProducts();

        assertSame(expected, actual);
        assertEquals(2, actual.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getProductShouldReturnProductFromOptional() {
        Product expected = new Product();
        when(productRepository.findById(1)).thenReturn(Optional.of(expected));

        Product actual = productServices.getProduct(1);

        assertSame(expected, actual);
        verify(productRepository, times(1)).findById(1);
    }

    @Test
    void updateProductShouldSetIdAndSaveWhenProductExistsWithMatchingId() {
        Product existing = new Product();
        existing.setPid(7);
        when(productRepository.findById(7)).thenReturn(Optional.of(existing));

        productServices.updateproduct(product, 7);

        assertEquals(7, product.getPid());
        verify(productRepository, times(1)).findById(7);
        verify(productRepository, times(1)).save(eq(product));
    }

    @Test
    void deleteProductShouldDelegateToRepositoryDeleteById() {
        productServices.deleteProduct(9);

        verify(productRepository, times(1)).deleteById(9);
    }

    @Test
    void getProductByNameShouldReturnProductWhenFound() {
        Product expected = new Product();
        when(productRepository.findByPname("phone")).thenReturn(expected);

        Product actual = productServices.getProductByName("phone");

        assertSame(expected, actual);
        verify(productRepository, times(1)).findByPname("phone");
    }

    @Test
    void getProductByNameShouldReturnNullWhenNotFound() {
        when(productRepository.findByPname("missing")).thenReturn(null);

        Product actual = productServices.getProductByName("missing");

        assertNull(actual);
        verify(productRepository, times(1)).findByPname("missing");
    }
}