package ca.mcgill.ecse321.petshelter.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.petshelter.model.Profile;

public interface ProfileRepository extends CrudRepository<Profile, String>{

	Profile findProfileByName(String name);

}