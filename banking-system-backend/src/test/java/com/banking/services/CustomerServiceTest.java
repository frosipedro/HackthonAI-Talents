package com.banking.services;

import com.banking.entities.Customer;
import com.banking.repositories.AccountRepository;
import com.banking.repositories.CustomerRepository;
import com.banking.utils.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testCustomer = new Customer();
        testCustomer.setId(1L);
        testCustomer.setName("Test User");
        testCustomer.setEmail("test@example.com");
        testCustomer.setBirthDate("1990-01-01");
    }

    @Test
    void createCustomer_Success() {
        when(customerRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(customerRepository.save(any(Customer.class))).thenReturn(testCustomer);

        Customer result = customerService.createCustomer(testCustomer);

        assertNotNull(result);
        assertEquals(testCustomer.getName(), result.getName());
        assertEquals(testCustomer.getEmail(), result.getEmail());
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void createCustomer_WithExistingEmail_ThrowsException() {
        when(customerRepository.findByEmail(anyString())).thenReturn(Optional.of(testCustomer));

        assertThrows(ValidationException.class, () -> {
            customerService.createCustomer(testCustomer);
        });

        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void getCustomerById_Success() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(testCustomer));

        Customer result = customerService.getCustomerById(1L);

        assertNotNull(result);
        assertEquals(testCustomer.getId(), result.getId());
        assertEquals(testCustomer.getName(), result.getName());
    }

    @Test
    void getCustomerById_NotFound_ThrowsException() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ValidationException.class, () -> {
            customerService.getCustomerById(1L);
        });
    }

    @Test
    void getAllCustomers_Success() {
        List<Customer> customers = Arrays.asList(testCustomer);
        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> result = customerService.getAllCustomers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testCustomer.getId(), result.get(0).getId());
    }

    @Test
    void updateCustomer_Success() {
        Customer updatedCustomer = new Customer();
        updatedCustomer.setName("Updated Name");
        updatedCustomer.setEmail("updated@example.com");
        updatedCustomer.setBirthDate("1990-01-01");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(testCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(updatedCustomer);

        Customer result = customerService.updateCustomer(1L, updatedCustomer);

        assertNotNull(result);
        assertEquals(updatedCustomer.getName(), result.getName());
        assertEquals(updatedCustomer.getEmail(), result.getEmail());
    }

    @Test
    void updateCustomer_NotFound_ThrowsException() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ValidationException.class, () -> {
            customerService.updateCustomer(1L, testCustomer);
        });

        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void deleteCustomer_Success() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(testCustomer));
        when(accountRepository.findByCustomerId(1L)).thenReturn(Arrays.asList());
        doNothing().when(customerRepository).deleteById(1L);
        doNothing().when(accountRepository).deleteAll(anyList());

        assertDoesNotThrow(() -> customerService.deleteCustomer(1L));

        verify(accountRepository).findByCustomerId(1L);
        verify(accountRepository).deleteAll(anyList());
        verify(customerRepository).deleteById(1L);
    }

    @Test
    void deleteCustomer_NotFound_ThrowsException() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ValidationException.class, () -> {
            customerService.deleteCustomer(1L);
        });

        verify(customerRepository, never()).deleteById(anyLong());
    }
}