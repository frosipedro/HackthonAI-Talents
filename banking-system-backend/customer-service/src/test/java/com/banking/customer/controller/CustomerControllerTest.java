package com.banking.customer.controller;

import com.banking.customer.model.Customer;
import com.banking.customer.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    private Customer customer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = new Customer(1L, "John Doe", "john.doe@example.com");
    }

    @Test
    void testGetAllCustomers() {
        when(customerService.findAll()).thenReturn(Collections.singletonList(customer));

        var response = customerController.getAllCustomers();

        assertEquals(1, response.size());
        assertEquals(customer.getName(), response.get(0).getName());
        verify(customerService, times(1)).findAll();
    }

    @Test
    void testGetCustomerById() {
        when(customerService.findById(1L)).thenReturn(Optional.of(customer));

        var response = customerController.getCustomerById(1L);

        assertTrue(response.isPresent());
        assertEquals(customer.getName(), response.get().getName());
        verify(customerService, times(1)).findById(1L);
    }

    @Test
    void testCreateCustomer() {
        when(customerService.save(any(Customer.class))).thenReturn(customer);

        var response = customerController.createCustomer(customer);

        assertEquals(customer.getName(), response.getName());
        verify(customerService, times(1)).save(any(Customer.class));
    }

    @Test
    void testUpdateCustomer() {
        when(customerService.update(anyLong(), any(Customer.class))).thenReturn(Optional.of(customer));

        var response = customerController.updateCustomer(1L, customer);

        assertTrue(response.isPresent());
        assertEquals(customer.getName(), response.get().getName());
        verify(customerService, times(1)).update(anyLong(), any(Customer.class));
    }

    @Test
    void testDeleteCustomer() {
        doNothing().when(customerService).delete(1L);

        customerController.deleteCustomer(1L);

        verify(customerService, times(1)).delete(1L);
    }
}