package ca.mcgill.ecse321.petshelter.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.petshelter.model.Donation;

public interface DonationRepository extends CrudRepository<Donation, Integer>{

	Donation findDonationById(Integer Id);

}