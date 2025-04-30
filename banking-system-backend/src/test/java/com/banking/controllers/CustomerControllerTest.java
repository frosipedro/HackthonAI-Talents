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

import static com.banking.utils.constants.MessageConstants.*;
import static org.mockito.ArgumentMatchers.any;
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
        testCustomer.setCpf("52998224725"); // CPF válido
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
                .andExpect(jsonPath("$.email").value(testCustomer.getEmail()))
                .andExpect(jsonPath("$.cpf").value(testCustomer.getCpf()));
    }

    @Test
    void createCustomer_InvalidData_BadRequest() throws Exception {
        Customer invalidCustomer = new Customer();
        // Campos obrigatórios ausentes

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
                .andExpect(jsonPath("$.email").value(testCustomer.getEmail()))
                .andExpect(jsonPath("$.cpf").value(testCustomer.getCpf()));
    }

    @Test
    void getCustomerById_NotFound() throws Exception {
        when(customerService.getCustomerById(1L)).thenThrow(new ValidationException(
                String.format(CUSTOMER_NOT_FOUND, 1L)));

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
                .andExpect(jsonPath("$[0].email").value(testCustomer.getEmail()))
                .andExpect(jsonPath("$[0].cpf").value(testCustomer.getCpf()));
    }

    @Test
    void updateCustomer_Success() throws Exception {
        Customer updatedCustomer = new Customer();
        updatedCustomer.setName("Updated Name");
        updatedCustomer.setEmail("updated@email.com");
        updatedCustomer.setBirthDate("1990-01-01");
        updatedCustomer.setCpf("52998224725");

        when(customerService.updateCustomer(any(Long.class), any(Customer.class))).thenReturn(updatedCustomer);

        mockMvc.perform(put("/api/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCustomer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(updatedCustomer.getName()))
                .andExpect(jsonPath("$.email").value(updatedCustomer.getEmail()))
                .andExpect(jsonPath("$.cpf").value(updatedCustomer.getCpf()));
    }

    @Test
    void deleteCustomer_Success() throws Exception {
        mockMvc.perform(delete("/api/customers/1"))
                .andExpect(status().isNoContent());
    }
}
