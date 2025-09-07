package com.infnet.tp2.service;

import com.infnet.tp2.model.Supplier;
import com.infnet.tp2.repository.SupplierRepository;
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
public class SupplierServiceTest {

    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private SupplierService supplierService;

    private Supplier supplier;

    @BeforeEach
    void setUp() {
        supplier = new Supplier();
        supplier.setId(1L);
        supplier.setName("Test Supplier");
        supplier.setCnpj("12345678901234");
        supplier.setEmail("supplier@example.com");
        supplier.setPhone("1234567890");
        supplier.setAddress("456 Business Ave");
    }

    @Test
    void findAll_ShouldReturnAllSuppliers() {
        when(supplierRepository.findAll()).thenReturn(Arrays.asList(supplier));

        List<Supplier> result = supplierService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(supplierRepository, times(1)).findAll();
    }

    @Test
    void findById_WhenSupplierExists_ShouldReturnSupplier() {
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));

        Optional<Supplier> result = supplierService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(supplier.getName(), result.get().getName());
        verify(supplierRepository, times(1)).findById(1L);
    }

    @Test
    void findById_WhenSupplierDoesNotExist_ShouldReturnEmpty() {
        when(supplierRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Supplier> result = supplierService.findById(999L);

        assertFalse(result.isPresent());
        verify(supplierRepository, times(1)).findById(999L);
    }

    @Test
    void createSupplier_ShouldReturnSavedSupplier() {
        when(supplierRepository.save(any(Supplier.class))).thenReturn(supplier);

        Supplier result = supplierService.createSupplier(supplier);

        assertNotNull(result);
        assertEquals(supplier.getName(), result.getName());
        verify(supplierRepository, times(1)).save(any(Supplier.class));
    }

    @Test
    void update_WhenSupplierExists_ShouldReturnUpdatedSupplier() {
        when(supplierRepository.existsById(1L)).thenReturn(true);
        when(supplierRepository.save(any(Supplier.class))).thenReturn(supplier);

        Optional<Supplier> result = supplierService.update(1L, supplier);

        assertTrue(result.isPresent());
        assertEquals(supplier.getName(), result.get().getName());
        verify(supplierRepository, times(1)).save(any(Supplier.class));
    }

    @Test
    void update_WhenSupplierDoesNotExist_ShouldReturnEmpty() {
        when(supplierRepository.existsById(999L)).thenReturn(false);

        Optional<Supplier> result = supplierService.update(999L, supplier);

        assertFalse(result.isPresent());
        verify(supplierRepository, never()).save(any(Supplier.class));
    }

    @Test
    void delete_WhenSupplierExists_ShouldReturnTrue() {
        when(supplierRepository.existsById(1L)).thenReturn(true);
        doNothing().when(supplierRepository).deleteById(1L);

        boolean result = supplierService.delete(1L);

        assertTrue(result);
        verify(supplierRepository, times(1)).deleteById(1L);
    }

    @Test
    void delete_WhenSupplierDoesNotExist_ShouldReturnFalse() {
        when(supplierRepository.existsById(999L)).thenReturn(false);

        boolean result = supplierService.delete(999L);

        assertFalse(result);
        verify(supplierRepository, never()).deleteById(any());
    }
}
