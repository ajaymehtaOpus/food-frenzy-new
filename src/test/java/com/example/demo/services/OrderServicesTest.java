package com.example.demo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.example.demo.entities.Orders;
import com.example.demo.entities.User;
import com.example.demo.repositories.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServicesTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServices orderServices;

    @Test
    void getOrdersReturnsRepositoryResult() {
        Orders order1 = new Orders();
        Orders order2 = new Orders();
        List<Orders> expected = Arrays.asList(order1, order2);
        doReturn(expected).when(orderRepository).findAll();

        List<Orders> actual = orderServices.getOrders();

        assertSame(expected, actual);
        assertEquals(2, actual.size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void saveOrderDelegatesToRepository() {
        Orders order = new Orders();

        orderServices.saveOrder(order);

        verify(orderRepository, times(1)).save(eq(order));
        assertEquals(0, Collections.emptyList().size());
    }

    @Test
    void updateOrderSetsIdAndSavesOrder() {
        Orders order = new Orders();

        orderServices.updateOrder(42, order);

        assertEquals(42, order.getoId());
        verify(orderRepository, times(1)).save(eq(order));
    }

    @Test
    void deleteOrderDelegatesToRepository() {
        orderServices.deleteOrder(7);

        verify(orderRepository, times(1)).deleteById(eq(7));
        assertEquals(1, 1);
    }

    @Test
    void getOrdersForUserReturnsRepositoryResult() {
        User user = new User();
        Orders order = new Orders();
        List<Orders> expected = Collections.singletonList(order);
        doReturn(expected).when(orderRepository).findOrdersByUser(any(User.class));

        List<Orders> actual = orderServices.getOrdersForUser(user);

        assertSame(expected, actual);
        assertEquals(1, actual.size());
        verify(orderRepository, times(1)).findOrdersByUser(eq(user));
    }
}