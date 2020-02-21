package ca.mcgill.ecse321.petshelter.controller;

import java.sql.Date;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import ca.mcgill.ecse321.petshelter.ErrorMessages;
import ca.mcgill.ecse321.petshelter.dto.*;
import ca.mcgill.ecse321.petshelter.model.*;
import ca.mcgill.ecse321.petshelter.service.*;

@CrossOrigin(origins = "*")
@RestController
public class PetShelterRestController {

	@Autowired
	private PetShelterService service;

	// GET Mappings // 

	// Get client -- For profile page
	@GetMapping(value = { "/profile/{email}", "/profile/{email}/" }) // This is a problem because @ can't be there
																	 // Easy fix suggestion -- We set a username attribute
	public ClientDTO getClientByEmail(@PathVariable("email") String email) throws IllegalArgumentException {

		Client client = service.getClient(email);

		if (client == null) {
			throw new IllegalArgumentException("Profile does not exist."); 
		}
		ClientDTO cDTO = convertToDTO(client.getEmail(), client.getFirstName(), client.getLastName());

		return cDTO;
	}

	// Convert to DTO functions // 

	// For profile page
	private ClientDTO convertToDTO(String email, String firstName, String lastName) {
		ClientDTO clientDTO = new ClientDTO(email, firstName, lastName); 
		return clientDTO; 
	}

}