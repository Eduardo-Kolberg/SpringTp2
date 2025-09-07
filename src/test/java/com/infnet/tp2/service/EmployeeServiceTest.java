package com.infnet.tp2.service;

import com.infnet.tp2.model.Employee;
import com.infnet.tp2.repository.EmployeeRepository;
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
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setId(1L);
        employee.setName("Jane Smith");
        employee.setPosition("Manager");
        employee.setDepartment("Sales");
        employee.setSalary(50000.0);
        employee.setEmail("jane@example.com");
    }

    @Test
    void findAll_ShouldReturnAllEmployees() {
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee));

        List<Employee> result = employeeService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void findById_WhenEmployeeExists_ShouldReturnEmployee() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Optional<Employee> result = employeeService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(employee.getName(), result.get().getName());
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void findById_WhenEmployeeDoesNotExist_ShouldReturnEmpty() {
        when(employeeRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Employee> result = employeeService.findById(999L);

        assertFalse(result.isPresent());
        verify(employeeRepository, times(1)).findById(999L);
    }

    @Test
    void createEmployee_ShouldReturnSavedEmployee() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee result = employeeService.createEmployee(employee);

        assertNotNull(result);
        assertEquals(employee.getName(), result.getName());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void update_WhenEmployeeExists_ShouldReturnUpdatedEmployee() {
        when(employeeRepository.existsById(1L)).thenReturn(true);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Optional<Employee> result = employeeService.update(1L, employee);

        assertTrue(result.isPresent());
        assertEquals(employee.getName(), result.get().getName());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void update_WhenEmployeeDoesNotExist_ShouldReturnEmpty() {
        when(employeeRepository.existsById(999L)).thenReturn(false);

        Optional<Employee> result = employeeService.update(999L, employee);

        assertFalse(result.isPresent());
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void delete_WhenEmployeeExists_ShouldReturnTrue() {
        when(employeeRepository.existsById(1L)).thenReturn(true);
        doNothing().when(employeeRepository).deleteById(1L);

        boolean result = employeeService.delete(1L);

        assertTrue(result);
        verify(employeeRepository, times(1)).deleteById(1L);
    }

    @Test
    void delete_WhenEmployeeDoesNotExist_ShouldReturnFalse() {
        when(employeeRepository.existsById(999L)).thenReturn(false);

        boolean result = employeeService.delete(999L);

        assertFalse(result);
        verify(employeeRepository, never()).deleteById(any());
    }
}
