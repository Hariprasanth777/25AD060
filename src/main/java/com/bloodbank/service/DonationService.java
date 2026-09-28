package com.bloodbank.service;

import com.bloodbank.entity.BloodUnit;
import com.bloodbank.entity.Donation;
import com.bloodbank.entity.Donor;
import com.bloodbank.repository.BloodUnitRepository;
import com.bloodbank.repository.DonationRepository;
import com.bloodbank.repository.DonorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonationService {

    private final DonationRepository donationRepository;
    private final BloodUnitRepository bloodUnitRepository;
    private final DonorRepository donorRepository;

    public DonationService(
            DonationRepository donationRepository,
            BloodUnitRepository bloodUnitRepository,
            DonorRepository donorRepository) {

        this.donationRepository = donationRepository;
        this.bloodUnitRepository = bloodUnitRepository;
        this.donorRepository = donorRepository;
    }

    public Donation addDonation(Donation donation) {

        // Get donor from database
        Long donorId = donation.getDonor().getDonorId();

        Donor donor = donorRepository
                .findById(donorId)
                .orElseThrow(() ->
                        new RuntimeException("Donor not found"));

        // Attach complete donor information
        donation.setDonor(donor);

        // Save donation
        Donation savedDonation = donationRepository.save(donation);

        // Update donor's last donation date
        donor.setLastDonationDate(
                savedDonation.getDonationDate()
        );

        donorRepository.save(donor);

        // Create blood units automatically
        for (int i = 0; i < savedDonation.getUnitsCollected(); i++) {

            BloodUnit bloodUnit = new BloodUnit();

            bloodUnit.setDonation(savedDonation);

            // Copy blood group from donor
            bloodUnit.setBloodGroup(
                    donor.getBloodGroup()
            );

            // Collection date
            bloodUnit.setCollectionDate(
                    savedDonation.getDonationDate()
            );

            // Project setting: 42-day expiry period
            bloodUnit.setExpiryDate(
                    savedDonation.getDonationDate().plusDays(42)
            );

            // New blood unit is available
            bloodUnit.setStatus("AVAILABLE");

            bloodUnitRepository.save(bloodUnit);
        }

        return savedDonation;
    }

    public List<Donation> getAllDonations() {
        return donationRepository.findAll();
    }

    public Donation getDonationById(Long id) {
        return donationRepository.findById(id).orElse(null);
    }
}