package com.infnet.tp2.service;

import com.infnet.tp2.model.Customer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final List<Customer> customers = new ArrayList<>();
    private Long currentId = 1L;

    public List<Customer> findAll() {
        return customers;
    }

    public Optional<Customer> findById(Long id) {
        return customers.stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst();
    }

    public Customer createCustomer(Customer customer) {
        customer.setId(currentId++);
        customers.add(customer);
        return customer;
    }

    public Optional<Customer> update(Long id, Customer customer) {
        Optional<Customer> existingCustomer = findById(id);
        if (existingCustomer.isPresent()) {
            customer.setId(id);
            customers.set(customers.indexOf(existingCustomer.get()), customer);
            return Optional.of(customer);
        }
        return Optional.empty();
    }

    public boolean delete(Long id) {
        return customers.removeIf(customer -> customer.getId().equals(id));
    }
}
