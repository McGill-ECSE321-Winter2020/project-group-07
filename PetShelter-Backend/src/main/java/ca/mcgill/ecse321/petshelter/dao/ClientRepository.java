package ca.mcgill.ecse321.petshelter.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.petshelter.model.Client;

public interface ClientRepository extends CrudRepository<Client, String>{

	Client findClientByEmail(String email);

}