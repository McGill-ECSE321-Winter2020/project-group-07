package ca.mcgill.ecse321.petshelter.controller;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


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
	public ClientDTO getClientByEmail(@RequestParam("email") String email) throws IllegalArgumentException { 
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

	// Verifying login
	@GetMapping(value = { "/login", "/login/" }) 
	public ProfileDTO verifyCredentials(@RequestParam("email") String email, @RequestParam("password") String password) throws IllegalArgumentException { 
		if (service.profileLogin(email, password).getIsLoggedIn()) {
			ProfileDTO profileDTO = convertToDTO(email, true); 
			return profileDTO; 
		} else {
			throw new IllegalArgumentException(ErrorMessages.loginFailed); // This really should never happen, but just in case
		}
	}


	// Youssef GET Mappings

	@GetMapping(value = {"/{posting}/applications", "/{posting}/applications/"})
	public List<ApplicationDTO> getPostingApplications(@PathVariable("posting") Posting posting) throws IllegalArgumentException{
		List<Application> applications = service.getPostingApplications(posting);
		return convertToDTOApplications(applications);
	}



	// Alex GET Mappings





	// Nicolas GET Mappings





	// Kaustav GET Mappings





	// POST Mappings // 

	// Rahul POST Mappings
	// Creating an account 
	@PostMapping(value = { "/createaccount", "/createaccount/" }) 
	public ClientDTO registerClient(@RequestParam("email") String email, @RequestParam("firstName") String firstName, 
									@RequestParam("lastName") String lastName, @RequestParam("dob") String dob_string, // Will be in format "yyyy-mm-dd"
									@RequestParam("phoneNumber") String phoneNumber, @RequestParam("address") String address,
									@RequestParam("password") String password) throws IllegalArgumentException, ParseException {
		
		// Changing date to SQL object
        Date dob = Date.valueOf(dob_string);//converting string into sql date  

		Client client = service.createClient(dob, email, password, phoneNumber, 
											 address, firstName, lastName);
											 
		return convertToDTO(client.getDateOfBirth(), client.getEmail(), client.getPhoneNumber(), client.getAddress(), 
							null, client.getIsLoggedIn(), client.getFirstName(), client.getLastName());
	}

	// Logging out
	@PostMapping(value = { "/logout", "/logout/"})
	public ProfileDTO logoutProfile(@RequestParam("email") String email) {
		if (!service.profileLogout(email).getIsLoggedIn()) {
			return convertToDTO(email, false); 
		} else {
			throw new IllegalArgumentException("Logout failed."); // Really should never happen, but just incase
		}
	}

	// Deleting an account
	// @PostMapping(value = { "/deleteaccount"})




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
	
	//Client Convert to DTOs

	// For viewing your own profile page -- Happens when you go to your page
	private ClientDTO convertToDTO(Date dob, String email, String phoneNumber, String address, List<PostingDTO> postings, 
			List<CommentDTO> comments, String firstName, String lastName, List<DonationDTO> donations, 
			List<MessageDTO> messages, List<ApplicationDTO> applications, boolean isLoggedIn) {
		ClientDTO clientDTO = new ClientDTO(dob, email, phoneNumber, address, postings, comments, isLoggedIn, firstName, lastName, 
				donations, messages, applications);
		return clientDTO;
	}

	// For viewing people's profile pages
	private ClientDTO convertToDTO(Date dob, String email, boolean isLoggedIn, String firstName, String lastName, List<PostingDTO> postings) {
		ClientDTO clientDTO = new ClientDTO(dob, email, isLoggedIn, firstName, lastName, postings); 
		return clientDTO; 
	}

	// For the person who posted your accepted application
	private ClientDTO convertToDTO(Date dob, String email, String phoneNumber, String address, boolean isLoggedIn, String firstName, String lastName) {
		ClientDTO clientDTO = new ClientDTO(dob, email, phoneNumber, address, isLoggedIn, firstName, lastName); 
		return clientDTO; 
	}

	// For updating profile information
	private ClientDTO convertToDTO(Date dob, String email, String password, String phoneNumber, String address, boolean isLoggedIn, String firstName, 
			String lastName) {
		ClientDTO clientDTO = new ClientDTO(dob, password, phoneNumber, address, isLoggedIn, firstName, lastName);
		return clientDTO;
	}



	//Application Convert to DTOs


	private ApplicationDTO convertToDTO(Application application) {
		ApplicationDTO applicationDTO = new ApplicationDTO();
		applicationDTO.setHomeType(application.getHomeType());
		applicationDTO.setIncomeRange(application.getIncomeRange());
		applicationDTO.setStatus(application.getStatus());
		applicationDTO.setNumberOfResidents(application.getNumberOfResidents());
		applicationDTO.setPosting(application.getPosting());
		Client client = application.getClient(); 
		applicationDTO.setClientDTO(convertToDTO(client.getDateOfBirth(), client.getEmail(), client.getPhoneNumber(), client.getAddress(), client.getIsLoggedIn(), client.getFirstName(), client.getLastName()));
		applicationDTO.setId(application.getId());
		return applicationDTO;
	}

	private List<ApplicationDTO> convertToDTOApplications(List<Application> applications){
		List<ApplicationDTO> applicationsDTO = new ArrayList<>();
		for(Application application : applications) {
			applicationsDTO.add(convertToDTO(application));
		}
		return applicationsDTO;
	}
	
	/**
	 * 
	 * @param message, that you want to convert to messageDTO
	 * @return messageDTO
	 */
	private MessageDTO convertToDTO(Message message) {
		MessageDTO messageDTO = new MessageDTO();
		messageDTO.setAdmin(message.getAdmin());
		messageDTO.setClient(convertToDTO(message.getClient().getDateOfBirth(),message.getClient().getEmail(), message.getClient().getPhoneNumber(), 
				message.getClient().getAddress(), message.getClient().getIsLoggedIn(),
				message.getClient().getFirstName(),message.getClient().getLastName()));
		messageDTO.setContent(message.getContent());
		messageDTO.setDate(message.getDate());
		messageDTO.setId(message.getId());
		return messageDTO;
		
	}
	/**
	 * 
	 * @param messages, a list of messages you want to convert to a list of messageDTO
	 * @return list of messageDTO
	 */
	private List<MessageDTO> convertToDTOMessage(List<Message> messages){
		List<MessageDTO> messageDTO = new ArrayList<>();
		for(Message message : messages) {
			messageDTO.add(convertToDTO(message));
		}
		return messageDTO;
	}
	
	/**
	 * 
	 * @param donation, that you want to convert to donationDTO
	 * @return donationDTO
	 */
	private DonationDTO convertToDTO(Donation donation) {
		DonationDTO donDTO = new DonationDTO();
		donDTO.setAmount(donation.getAmount());
		Client client = donation.getClient();
		donDTO.setClient(convertToDTO(client.getDateOfBirth(),client.getEmail(), client.getPhoneNumber(), 
				client.getAddress(), client.getIsLoggedIn(),
				client.getFirstName(),client.getLastName()));
		return donDTO;
	}
	
	//Comment Convert to DTOs

	private CommentDTO convertToDTO(Comment comment) {
		CommentDTO commentDTO = new CommentDTO(comment.getId(), comment.getDate(), convertToDTO(comment.getProfile()),
				convertToDTO(comment.getPosting()), comment.getContent());
		return commentDTO;
	}

	private List<CommentDTO> convertToDTOComments(List<Comment> comments){
		List<CommentDTO> commentsDTO = new ArrayList<CommentDTO>();
		for(Comment comment : comments) {
			commentsDTO.add(convertToDTO(comment));
		}
		return commentsDTO;
	}
	
	//Posting Convert to DTOs

	private PostingDTO convertToDTO(Posting posting) {
		PostingDTO postingDTO = new PostingDTO(posting.getId(), posting.getDate(), posting.getPicture(),
				posting.getDescription(), posting.getPetName(), posting.getPetBreed(), posting.getPetDateOfBirth(),convertToDTO(posting.getProfile()),
				convertToDTOApplications(service.toList(posting.getApplication())), convertToDTOComments(service.toList(posting.getComment())));
		return postingDTO;
	}

	private List<PostingDTO> convertToDTOPostings(List<Posting> postings){
		List<PostingDTO> postingsDTO = new ArrayList<>();
		for(Posting posting : postings) {
			postingsDTO.add(convertToDTO(posting));
		}
		return postingsDTO;
	}
	
	//Profile Convert to DTOs
	
	//needed for messages and comments
	private ProfileDTO convertToDTO(Profile profile) {
		ProfileDTO profileDTO = new ProfileDTO();
		profileDTO.setEmail(profile.getEmail());
		profileDTO.setAddress(profile.getAddress());
		profileDTO.setPhoneNumber(profile.getPhoneNumber());
		profileDTO.setDateOfBirth(profile.getDateOfBirth());
		profileDTO.setPostings(convertToDTOPostings(service.toList(profile.getPostings())));
		return profileDTO;
	}

	//For logging in 
	private ProfileDTO convertToDTO(String email, boolean isLoggedIn) {
		ProfileDTO profileDTO = new ProfileDTO();
		profileDTO.setEmail(email);
		profileDTO.setLoggedIn(isLoggedIn);
		return profileDTO;
	}

}
