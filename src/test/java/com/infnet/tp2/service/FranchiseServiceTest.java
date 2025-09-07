package com.infnet.tp2.service;

import com.infnet.tp2.model.Franchise;
import com.infnet.tp2.repository.FranchiseRepository;
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
public class FranchiseServiceTest {

    @Mock
    private FranchiseRepository franchiseRepository;

    @InjectMocks
    private FranchiseService franchiseService;

    private Franchise franchise;

    @BeforeEach
    void setUp() {
        franchise = new Franchise();
        franchise.setId(1L);
        franchise.setName("Test Franchise");
        franchise.setLocation("Test Location");
        franchise.setAnnualRevenue(100000.0);
        franchise.setContactPerson("John Doe");
        franchise.setPhoneNumber("123-456-7890");
        franchise.setActive(true);
    }

    @Test
    void getAllFranchises() {
        when(franchiseRepository.findAll()).thenReturn(Arrays.asList(franchise));
        List<Franchise> franchises = franchiseService.getAllFranchises();
        assertNotNull(franchises);
        assertEquals(1, franchises.size());
        verify(franchiseRepository, times(1)).findAll();
    }

    @Test
    void getFranchiseById() {
        when(franchiseRepository.findById(1L)).thenReturn(Optional.of(franchise));
        Optional<Franchise> found = franchiseService.getFranchiseById(1L);
        assertTrue(found.isPresent());
        assertEquals("Test Franchise", found.get().getName());
    }

    @Test
    void saveFranchise() {
        when(franchiseRepository.save(any(Franchise.class))).thenReturn(franchise);
        Franchise saved = franchiseService.saveFranchise(franchise);
        assertNotNull(saved);
        assertEquals("Test Franchise", saved.getName());
        verify(franchiseRepository, times(1)).save(any(Franchise.class));
    }

    @Test
    void updateFranchise() {
        Franchise updatedFranchise = new Franchise();
        updatedFranchise.setName("Updated Franchise");
        updatedFranchise.setLocation("Updated Location");

        when(franchiseRepository.findById(1L)).thenReturn(Optional.of(franchise));
        when(franchiseRepository.save(any(Franchise.class))).thenReturn(updatedFranchise);

        Franchise result = franchiseService.updateFranchise(1L, updatedFranchise);
        assertNotNull(result);
        assertEquals("Updated Franchise", result.getName());
        verify(franchiseRepository, times(1)).save(any(Franchise.class));
    }

    @Test
    void deleteFranchise() {
        doNothing().when(franchiseRepository).deleteById(1L);
        franchiseService.deleteFranchise(1L);
        verify(franchiseRepository, times(1)).deleteById(1L);
    }
}
