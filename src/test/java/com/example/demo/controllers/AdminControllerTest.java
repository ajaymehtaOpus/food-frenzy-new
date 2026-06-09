package com.example.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import com.example.demo.count.Logic;
import com.example.demo.entities.Admin;
import com.example.demo.entities.Orders;
import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.loginCredentials.AdminLogin;
import com.example.demo.loginCredentials.UserLogin;
import com.example.demo.services.AdminServices;
import com.example.demo.services.OrderServices;
import com.example.demo.services.ProductServices;
import com.example.demo.services.UserServices;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    private UserServices services;

    @Mock
    private AdminServices adminServices;

    @Mock
    private ProductServices productServices;

    @Mock
    private OrderServices orderServices;

    @Mock
    private Model model;

    @InjectMocks
    private AdminController controller;

    @Test
    void getAllData_whenCredentialsValid_redirectsToAdminServices() {
        AdminLogin login = new AdminLogin();
        login.setEmail("admin@example.com");
        login.setPassword("secret");

        doReturn(true).when(adminServices).validateAdminCredentials("admin@example.com", "secret");

        String view = controller.getAllData(login, model);

        assertEquals("redirect:/admin/services", view);
        verify(adminServices).validateAdminCredentials("admin@example.com", "secret");
        verifyNoInteractions(model);
    }

    @Test
    void getAllData_whenCredentialsInvalid_returnsLoginAndAddsError() {
        AdminLogin login = new AdminLogin();
        login.setEmail("admin@example.com");
        login.setPassword("wrong");

        doReturn(false).when(adminServices).validateAdminCredentials("admin@example.com", "wrong");

        String view = controller.getAllData(login, model);

        assertEquals("Login", view);
        verify(model).addAttribute("error", "Invalid email or password");
        verify(adminServices).validateAdminCredentials("admin@example.com", "wrong");
    }

    @Test
    void userLogin_whenCredentialsValid_returnsBuyProductAndAddsOrdersAndName() {
        UserLogin login = new UserLogin();
        login.setUserEmail("user@example.com");
        login.setUserPassword("pwd");

        User user = new User();
        user.setUname("John");
        doReturn(true).when(services).validateLoginCredentials("user@example.com", "pwd");
        doReturn(user).when(services).getUserByEmail("user@example.com");
        doReturn(Collections.emptyList()).when(orderServices).getOrdersForUser(any(User.class));

        String view = controller.userLogin(login, model);

        assertEquals("BuyProduct", view);
        verify(model).addAttribute(eq("orders"), any(List.class));
        verify(model).addAttribute("name", "John");
        verify(services).validateLoginCredentials("user@example.com", "pwd");
        verify(services).getUserByEmail("user@example.com");
        verify(orderServices).getOrdersForUser(user);
    }

    @Test
    void userLogin_whenCredentialsInvalid_returnsLoginAndAddsError() {
        UserLogin login = new UserLogin();
        login.setUserEmail("user@example.com");
        login.setUserPassword("bad");

        doReturn(false).when(services).validateLoginCredentials("user@example.com", "bad");

        String view = controller.userLogin(login, model);

        assertEquals("Login", view);
        verify(model).addAttribute("error2", "Invalid email or password");
        verify(services).validateLoginCredentials("user@example.com", "bad");
        verifyNoMoreInteractions(services);
    }

    @Test
    void seachHandler_whenProductMissing_returnsBuyProductAndAddsMessageProductAndOrders() {
        doReturn(null).when(productServices).getProductByName("missing");
        doReturn(Collections.emptyList()).when(orderServices).getOrdersForUser(any());

        String view = controller.seachHandler("missing", model);

        assertEquals("BuyProduct", view);
        verify(model).addAttribute("message", "SORRY...!  Product Unavailable");
        verify(model).addAttribute("product", null);
        verify(model).addAttribute(eq("orders"), any(List.class));
        verify(productServices).getProductByName("missing");
    }

    @Test
    void seachHandler_whenProductExists_returnsBuyProductAndAddsOrdersAndProduct() {
        Product product = new Product();
        doReturn(product).when(productServices).getProductByName("phone");
        doReturn(Collections.emptyList()).when(orderServices).getOrdersForUser(any());

        String view = controller.seachHandler("phone", model);

        assertEquals("BuyProduct", view);
        verify(model).addAttribute(eq("orders"), any(List.class));
        verify(model).addAttribute("product", product);
        verify(productServices).getProductByName("phone");
    }

    @Test
    void returnBack_addsAllCollectionsAndReturnsAdminPage() {
        List<User> users = Collections.singletonList(new User());
        List<Admin> admins = Collections.singletonList(new Admin());
        List<Product> products = Collections.singletonList(new Product());
        List<Orders> orders = Collections.singletonList(new Orders());

        doReturn(users).when(services).getAllUser();
        doReturn(admins).when(adminServices).getAll();
        doReturn(products).when(productServices).getAllProducts();
        doReturn(orders).when(orderServices).getOrders();

        String view = controller.returnBack(model);

        assertEquals("Admin_Page", view);
        verify(model).addAttribute("users", users);
        verify(model).addAttribute("admins", admins);
        verify(model).addAttribute("products", products);
        verify(model).addAttribute("orders", orders);
    }

    @Test
    void addAdminPage_returnsAddAdminView() {
        assertEquals("Add_Admin", controller.addAdminPage());
    }

    @Test
    void addAdmin_delegatesAndRedirects() {
        Admin admin = new Admin();

        String view = controller.addAdmin(admin);

        assertEquals("redirect:/admin/services", view);
        verify(adminServices).addAdmin(admin);
    }

    @Test
    void update_addsAdminAndReturnsUpdateView() {
        Admin admin = new Admin();
        doReturn(admin).when(adminServices).getAdmin(7);

        String view = controller.update(7, model);

        assertEquals("Update_Admin", view);
        verify(model).addAttribute("admin", admin);
        verify(adminServices).getAdmin(7);
    }

    @Test
    void updateAdmin_delegatesAndRedirects() {
        Admin admin = new Admin();

        String view = controller.updateAdmin(admin, 9);

        assertEquals("redirect:/admin/services", view);
        verify(adminServices).update(admin, 9);
    }

    @Test
    void deleteAdmin_delegatesAndRedirects() {
        String view = controller.deleteAdmin(11);

        assertEquals("redirect:/admin/services", view);
        verify(adminServices).delete(11);
    }

    @Test
    void addProduct_returnsAddProductView() {
        assertEquals("Add_Product", controller.addProduct());
    }

    @Test
    void updateProduct_addsProductAndReturnsUpdateView() {
        Product product = new Product();
        doReturn(product).when(productServices).getProduct(3);

        String view = controller.updateProduct(3, model);

        assertEquals("Update_Product", view);
        verify(model).addAttribute("product", product);
        verify(productServices).getProduct(3);
    }

    @Test
    void addUser_returnsAddUserView() {
        assertEquals("Add_User", controller.addUser());
    }

    @Test
    void updateUserPage_addsUserAndReturnsUpdateView() {
        User user = new User();
        doReturn(user).when(services).getUser(5);

        String view = controller.updateUserPage(5, model);

        assertEquals("Update_User", view);
        verify(model).addAttribute("user", user);
        verify(services).getUser(5);
    }

    @Test
    void orderHandler_calculatesTotalSetsFieldsSavesOrderAndReturnsSuccess() {
        Orders order = new Orders();
        order.setoPrice(12.5);
        order.setoQuantity(4);

        String view = controller.orderHandler(order, model);

        assertEquals("Order_success", view);
        verify(orderServices).saveOrder(any(Orders.class));
        verify(model).addAttribute(eq("amount"), any(Double.class));

        ArgumentCaptor<Orders> captor = ArgumentCaptor.forClass(Orders.class);
        verify(orderServices).saveOrder(captor.capture());
        Orders saved = captor.getValue();
        assertEquals(50.0, saved.getTotalAmmout());
        assertEquals(order.getoPrice(), saved.getoPrice());
        assertEquals(order.getoQuantity(), saved.getoQuantity());
        assertEquals(Logic.countTotal(12.5, 4), saved.getTotalAmmout());
    }

    @Test
    void back_addsOrdersAndReturnsBuyProduct() {
        List<Orders> orders = Collections.singletonList(new Orders());
        doReturn(orders).when(orderServices).getOrdersForUser(any());

        String view = controller.back(model);

        assertEquals("BuyProduct", view);
        verify(model).addAttribute("orders", orders);
        verify(orderServices).getOrdersForUser(any());
    }
}