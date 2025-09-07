package com.infnet.tp2.service;

import com.infnet.tp2.model.Customer;
import com.infnet.tp2.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("john@example.com");
        customer.setCpf("12345678901");
        customer.setPhone("1234567890");
        customer.setAddress("123 Main St");
    }

    @Test
    void findAll_ShouldReturnAllCustomers() {
        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer));

        List<Customer> result = customerService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void findById_WhenCustomerExists_ShouldReturnCustomer() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Optional<Customer> result = customerService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(customer.getName(), result.get().getName());
        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    void findById_WhenCustomerDoesNotExist_ShouldReturnEmpty() {
        when(customerRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Customer> result = customerService.findById(999L);

        assertFalse(result.isPresent());
        verify(customerRepository, times(1)).findById(999L);
    }

    @Test
    void createCustomer_ShouldReturnSavedCustomer() {
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer result = customerService.createCustomer(customer);

        assertNotNull(result);
        assertEquals(customer.getName(), result.getName());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void update_WhenCustomerExists_ShouldReturnUpdatedCustomer() {
        when(customerRepository.existsById(1L)).thenReturn(true);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Optional<Customer> result = customerService.update(1L, customer);

        assertTrue(result.isPresent());
        assertEquals(customer.getName(), result.get().getName());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void update_WhenCustomerDoesNotExist_ShouldReturnEmpty() {
        when(customerRepository.existsById(999L)).thenReturn(false);

        Optional<Customer> result = customerService.update(999L, customer);

        assertFalse(result.isPresent());
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void delete_WhenCustomerExists_ShouldReturnTrue() {
        when(customerRepository.existsById(1L)).thenReturn(true);
        doNothing().when(customerRepository).deleteById(1L);

        boolean result = customerService.delete(1L);

        assertTrue(result);
        verify(customerRepository, times(1)).deleteById(1L);
    }

    @Test
    void delete_WhenCustomerDoesNotExist_ShouldReturnFalse() {
        when(customerRepository.existsById(999L)).thenReturn(false);

        boolean result = customerService.delete(999L);

        assertFalse(result);
        verify(customerRepository, never()).deleteById(any());
    }
}
