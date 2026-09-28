package com.bloodbank.service;

import com.bloodbank.entity.Donor;
import com.bloodbank.repository.DonorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class DonorService {

    private final DonorRepository donorRepository;

    public DonorService(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
    }

    public Donor addDonor(Donor donor) {
        return donorRepository.save(donor);
    }

    public List<Donor> getAllDonors() {
        return donorRepository.findAll();
    }

    public Donor getDonorById(Long id) {
        return donorRepository.findById(id).orElse(null);
    }

    // Check donor eligibility
    public String checkEligibility(Long id) {

        Donor donor = donorRepository.findById(id).orElse(null);

        if (donor == null) {
            return "Donor not found";
        }

        // If the donor has never donated before
        if (donor.getLastDonationDate() == null) {
            return "Eligible to donate";
        }

        LocalDate today = LocalDate.now();

        long daysSinceLastDonation =
                ChronoUnit.DAYS.between(
                        donor.getLastDonationDate(),
                        today
                );

        if (daysSinceLastDonation >= 90) {
            return "Eligible to donate";
        } else {
            long remainingDays = 90 - daysSinceLastDonation;

            return "Not eligible. Please wait "
                    + remainingDays
                    + " more days.";
        }
    }
}