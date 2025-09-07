package com.infnet.tp2.service;

import com.infnet.tp2.model.Franchise;
import com.infnet.tp2.repository.FranchiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FranchiseService {

    @Autowired
    private FranchiseRepository franchiseRepository;

    public List<Franchise> getAllFranchises() {
        return franchiseRepository.findAll();
    }

    public Optional<Franchise> getFranchiseById(Long id) {
        return franchiseRepository.findById(id);
    }

    public Franchise saveFranchise(Franchise franchise) {
        return franchiseRepository.save(franchise);
    }

    public void deleteFranchise(Long id) {
        franchiseRepository.deleteById(id);
    }

    public Franchise updateFranchise(Long id, Franchise franchiseDetails) {
        Franchise franchise = franchiseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Franchise not found with id: " + id));

        franchise.setName(franchiseDetails.getName());
        franchise.setLocation(franchiseDetails.getLocation());
        franchise.setAnnualRevenue(franchiseDetails.getAnnualRevenue());
        franchise.setContactPerson(franchiseDetails.getContactPerson());
        franchise.setPhoneNumber(franchiseDetails.getPhoneNumber());
        franchise.setActive(franchiseDetails.isActive());

        return franchiseRepository.save(franchise);
    }
}
