package com.banking.controllers;

import com.banking.entities.Customer;
import com.banking.services.CustomerService;
import com.banking.utils.exception.ValidationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        testCustomer = new Customer();
        testCustomer.setId(1L);
        testCustomer.setName("Test User");
        testCustomer.setEmail("test@example.com");
        testCustomer.setBirthDate("1990-01-01");
    }

    @Test
    void createCustomer_Success() throws Exception {
        when(customerService.createCustomer(any(Customer.class))).thenReturn(testCustomer);

        mockMvc.perform(post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCustomer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(testCustomer.getId()))
                .andExpect(jsonPath("$.name").value(testCustomer.getName()))
                .andExpect(jsonPath("$.email").value(testCustomer.getEmail()));
    }

    @Test
    void createCustomer_InvalidData_BadRequest() throws Exception {
        Customer invalidCustomer = new Customer();
        // Missing required fields

        mockMvc.perform(post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidCustomer)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getCustomerById_Success() throws Exception {
        when(customerService.getCustomerById(1L)).thenReturn(testCustomer);

        mockMvc.perform(get("/api/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testCustomer.getId()))
                .andExpect(jsonPath("$.name").value(testCustomer.getName()))
                .andExpect(jsonPath("$.email").value(testCustomer.getEmail()));
    }

    @Test
    void getCustomerById_NotFound() throws Exception {
        when(customerService.getCustomerById(1L)).thenThrow(new ValidationException("Customer not found"));

        mockMvc.perform(get("/api/customers/1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllCustomers_Success() throws Exception {
        when(customerService.getAllCustomers()).thenReturn(Arrays.asList(testCustomer));

        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(testCustomer.getId()))
                .andExpect(jsonPath("$[0].name").value(testCustomer.getName()))
                .andExpect(jsonPath("$[0].email").value(testCustomer.getEmail()));
    }

    @Test
    void updateCustomer_Success() throws Exception {
        when(customerService.updateCustomer(eq(1L), any(Customer.class))).thenReturn(testCustomer);

        mockMvc.perform(put("/api/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCustomer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testCustomer.getId()))
                .andExpect(jsonPath("$.name").value(testCustomer.getName()))
                .andExpect(jsonPath("$.email").value(testCustomer.getEmail()));
    }

    @Test
    void deleteCustomer_Success() throws Exception {
        mockMvc.perform(delete("/api/customers/1"))
                .andExpect(status().isNoContent());
    }
}