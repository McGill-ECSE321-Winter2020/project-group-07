package ca.mcgill.ecse321.petshelter.controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.petshelter.ErrorMessages;
import ca.mcgill.ecse321.petshelter.dto.*;
import ca.mcgill.ecse321.petshelter.model.*;
import ca.mcgill.ecse321.petshelter.service.PetShelterService;

@CrossOrigin(origins = "*")
@RestController
public class PetShelterRestController {

	@Autowired
	private PetShelterService service;



	// GET Mappings // 

	// Get client -- For someone viewing a profile page
	@GetMapping(value = { "/profile", "/profile/" }) 
	public ClientDTO getClientByEmail(@RequestParam("email") String email) throws IllegalArgumentException { // Is it okay to have @?
		Client client = service.getClient(email);
		if (client == null) {
			throw new IllegalArgumentException(ErrorMessages.accountDoesNotExist);
		}
		// On a profile page, people are able to view the dob, email, full name, and current postings of the user
		//ClientDTO cDTO = convertToDTO(client.getDateOfBirth(), client.getEmail(), client.getFirstName(), 
		//							  client.getLastName(), client.getPostings());
		//return cDTO;
		return null;
	}



	// POST Mappings // 

	// Creating an account 
	@PostMapping(value = { "/createaccount", "/createaccount/" }) // Probably need to switch this to @RequestBody
	public ClientDTO registerClient(@RequestParam("email") String email, @RequestParam("firstName") String firstName, 
									@RequestParam("lastName") String lastName, @RequestParam("dob") String dob, // Will be in format "dd-MM-yyyy"
									@RequestParam("phoneNumber") String phoneNumber, @RequestParam("address") String address,
									@RequestParam("password") String password) throws IllegalArgumentException, ParseException {
		
		// Changing date to SQL object --> Look into Spring DateTimeFormat, may be easier
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date dob_util = sdf.parse(dob);
		java.sql.Date dob_sql = new java.sql.Date(dob_util.getTime()); 
		
		Client client = service.createClient(dob_sql, email, password, phoneNumber, 
											 address, firstName, lastName);

//		return convertToDTO(client.getDateOfBirth(), client.getEmail(), client.getPhoneNumber(), client.getAddress(), 
//							client.getPostings(), client.getComments(), client.getFirstName(), client.getLastName(), 
//							client.getDonations(), client.getMessages(), client.getApplications());
		return null;
	}



	// Convert to DTO functions // 

	// For viewing your own profile page -- Happens when you go to your page
	private ClientDTO convertToDTO(Date dob, String email, String phoneNumber, String address, Set<PostingDTO> postings, 
								   Set<CommentDTO> comments, String firstName, String lastName, Set<DonationDTO> donations, 
								   Set<MessageDTO> messages, Set<Application> applications) {
		ClientDTO clientDTO = new ClientDTO(dob, email, phoneNumber, address, postings, comments, firstName, lastName, 
											donations, messages, applications);
		return clientDTO;
	}

	// For viewing people's profile pages
	private ClientDTO convertToDTO(Date dob, String email, String firstName, String lastName, Set<PostingDTO> postings) {
		ClientDTO clientDTO = new ClientDTO(dob, email, firstName, lastName, postings); 
		return clientDTO; 
	}

	// For the person who posted your accepted application
	private ClientDTO convertToDTO(Date dob, String email, String phoneNumber, String address, String firstName, String lastName) {
		ClientDTO clientDTO = new ClientDTO(dob, email, phoneNumber, address, firstName, lastName); 
		return clientDTO; 
	}

	// For updating profile information
	private ClientDTO convertToDTO(Date dob, String email, String password, String phoneNumber, String address, String firstName, // May have to remove email
								   String lastName) {
		ClientDTO clientDTO = new ClientDTO(dob, password, phoneNumber, address, firstName, lastName);
		return clientDTO;
	}

}