package com.banking.services;

import com.banking.entities.Customer;
import com.banking.repositories.AccountRepository;
import com.banking.repositories.CustomerRepository;
import com.banking.utils.constants.MessageConstants;
import com.banking.utils.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
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
        Customer customer = new Customer();
        customer.setName("Test User");
        customer.setEmail("test@example.com");
        customer.setBirthDate("1990-01-01");

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer savedCustomer = customerService.createCustomer(customer);
        assertNotNull(savedCustomer);
        verify(customerRepository).save(customer);
    }

    @Test
    void createCustomer_WithValidEmail_Success() {
        Customer customer = new Customer();
        customer.setName("Test User");
        customer.setEmail("valid.email@domain.com");
        customer.setBirthDate("1990-01-01");

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer savedCustomer = customerService.createCustomer(customer);
        assertNotNull(savedCustomer);
        verify(customerRepository).save(customer);
    }

    @Test
    void createCustomer_WithInvalidEmail_ThrowsException() {
        Customer newCustomer = new Customer();
        newCustomer.setName("Test User");
        newCustomer.setEmail("invalid-email");
        newCustomer.setBirthDate(LocalDate.now().minusYears(20).toString());

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            customerService.createCustomer(newCustomer);
        });

        assertEquals(MessageConstants.INVALID_EMAIL_FORMAT, exception.getMessage());
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void createCustomer_WithDuplicateEmail_ThrowsException() {
        Customer existingCustomer = new Customer();
        existingCustomer.setEmail("existing@email.com");

        Customer newCustomer = new Customer();
        newCustomer.setName("Test User");
        newCustomer.setEmail("existing@email.com");
        newCustomer.setBirthDate(LocalDate.now().minusYears(20).toString());

        when(customerRepository.findByEmail("existing@email.com")).thenReturn(Optional.of(existingCustomer));

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            customerService.createCustomer(newCustomer);
        });

        assertEquals(MessageConstants.EMAIL_ALREADY_EXISTS, exception.getMessage());
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void createCustomer_WithFutureBirthDate_ThrowsException() {
        Customer customerWithFutureDate = new Customer();
        customerWithFutureDate.setName("Test User");
        customerWithFutureDate.setEmail("test@example.com");
        customerWithFutureDate.setBirthDate(LocalDate.now().plusDays(1).toString());

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            customerService.createCustomer(customerWithFutureDate);
        });

        assertEquals(MessageConstants.FUTURE_DATE_NOT_ALLOWED, exception.getMessage());
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

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            customerService.getCustomerById(1L);
        });

        assertEquals(String.format(MessageConstants.CUSTOMER_NOT_FOUND, 1L), exception.getMessage());
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

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            customerService.updateCustomer(1L, testCustomer);
        });

        assertEquals(String.format(MessageConstants.CUSTOMER_NOT_FOUND, 1L), exception.getMessage());
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void updateCustomerPartial_Success() {
        Customer partialCustomer = new Customer();
        partialCustomer.setName("Updated Name");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(testCustomer));
        when(customerRepository.save(any(Customer.class))).thenAnswer(i -> i.getArguments()[0]);

        Customer result = customerService.updateCustomerPartial(1L, partialCustomer);

        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
        assertEquals(testCustomer.getEmail(), result.getEmail());
        assertEquals(testCustomer.getBirthDate(), result.getBirthDate());
    }

    @Test
    void updateCustomerPartial_WithInvalidEmail_ThrowsException() {
        Customer partialUpdate = new Customer();
        partialUpdate.setEmail("invalid-email");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(testCustomer));

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            customerService.updateCustomerPartial(1L, partialUpdate);
        });

        assertEquals(MessageConstants.INVALID_EMAIL_FORMAT, exception.getMessage());
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void updateCustomerPartial_WithFutureBirthDate_ThrowsException() {
        Customer partialUpdate = new Customer();
        partialUpdate.setBirthDate(LocalDate.now().plusDays(1).toString());

        when(customerRepository.findById(1L)).thenReturn(Optional.of(testCustomer));

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            customerService.updateCustomerPartial(1L, partialUpdate);
        });

        assertEquals(MessageConstants.FUTURE_DATE_NOT_ALLOWED, exception.getMessage());
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

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            customerService.deleteCustomer(1L);
        });

        assertEquals(String.format(MessageConstants.CUSTOMER_NOT_FOUND, 1L), exception.getMessage());
        verify(customerRepository, never()).deleteById(anyLong());
    }
}
