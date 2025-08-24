package com.infnet.tp2.service;

import com.infnet.tp2.model.Supplier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {
    private final List<Supplier> suppliers = new ArrayList<>();
    private Long currentId = 1L;

    public List<Supplier> findAll() {
        return suppliers;
    }

    public Optional<Supplier> findById(Long id) {
        return suppliers.stream()
                .filter(supplier -> supplier.getId().equals(id))
                .findFirst();
    }

    public Supplier createSupplier(Supplier supplier) {
        supplier.setId(currentId++);
        suppliers.add(supplier);
        return supplier;
    }

    public Optional<Supplier> update(Long id, Supplier supplier) {
        Optional<Supplier> existingSupplier = findById(id);
        if (existingSupplier.isPresent()) {
            supplier.setId(id);
            suppliers.set(suppliers.indexOf(existingSupplier.get()), supplier);
            return Optional.of(supplier);
        }
        return Optional.empty();
    }

    public boolean delete(Long id) {
        return suppliers.removeIf(supplier -> supplier.getId().equals(id));
    }
}
