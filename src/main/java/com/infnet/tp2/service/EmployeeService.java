package com.infnet.tp2.service;

import com.infnet.tp2.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final List<Employee> employees = new ArrayList<>();
    private Long currentId = 1L;

    public List<Employee> findAll() {
        return employees;
    }

    public Optional<Employee> findById(Long id) {
        return employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst();
    }

    public Employee createEmployee(Employee employee) {
        employee.setId(currentId++);
        employees.add(employee);
        return employee;
    }

    public Optional<Employee> update(Long id, Employee employee) {
        Optional<Employee> existingEmployee = findById(id);
        if (existingEmployee.isPresent()) {
            employee.setId(id);
            employees.set(employees.indexOf(existingEmployee.get()), employee);
            return Optional.of(employee);
        }
        return Optional.empty();
    }

    public boolean delete(Long id) {
        return employees.removeIf(employee -> employee.getId().equals(id));
    }
}
