package ca.mcgill.ecse321.petshelter.controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	// Rahul GET Mappings
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

		ClientDTO cDTO = convertToDTO(client.getDateOfBirth(), client.getEmail(), client.getFirstName(), 
				client.getLastName(), client.getPostings());
		return cDTO;

	}




	// Youssef GET Mappings

	@GetMapping(value = {"/{posting}/applications", "/{posting}/applications/"})
	public List<ApplicationDTO> getPostingApplications(@PathVariable("posting") Posting posting) throws IllegalArgumentException{
		List<Application> applications = service.getPostingApplications(posting);
		return convertToDTO(applications);
	}



	// Alex GET Mappings





	// Nicolas GET Mappings





	// Kaustav GET Mappings





	// POST Mappings // 

	// Rahul POST Mappings
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






	// Youssef POST Mappings


	@PostMapping(value = {"/createapplication", "/createapplication"})
	public ApplicationDTO createApplication(@RequestParam("client") Client client, @RequestParam("posting") Posting posting, 
											@RequestParam("homeType") HomeType homeType, @RequestParam("incomeRange") IncomeRange incomeRange,
											@RequestParam("numberOfResidents") Integer numberOfResidents) throws IllegalArgumentException{
		
		Application application = service.createApplication(client, posting, homeType, incomeRange, numberOfResidents);
		return convertToDTO(application);
	}



	// Alex POST Mappings





	// Nicolas POST Mappings




	// Kaustav POST Mappings





	// Convert to DTO functions // 

	// Rahul DTOs

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





	// Youssef converToDTOs

	private ApplicationDTO convertToDTO(Application application) {
		ApplicationDTO applicationDTO = new ApplicationDTO();
		applicationDTO.setHomeType(application.getHomeType());
		applicationDTO.setIncomeRange(application.getIncomeRange());
		applicationDTO.setStatus(application.getStatus());
		applicationDTO.setNumberOfResidents(application.getNumberOfResidents());
		applicationDTO.setPosting(application.getPosting());
		Client client = application.getClient(); 
		applicationDTO.setClientDTO(convertToDTO(client.getDateOfBirth(), client.getEmail(), client.getPhoneNumber(), client.getAddress(), client.getFirstName(), client.getLastName()));
		applicationDTO.setId(application.getId());
		return applicationDTO;
	}
	
	private List<ApplicationDTO> convertToDTO(List<Application> applications){
		List<ApplicationDTO> applicationsDTO = new ArrayList<>();
		for(Application application : applications) {
			applicationsDTO.add(convertToDTO(application));
		}
		return applicationsDTO;
	}



	// Alex ConvertToDTOs





	// Nicolas ConvertToDTOs





	// Kaustav ConvertToDTOs
}
