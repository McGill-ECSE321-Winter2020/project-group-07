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
import org.springframework.web.bind.annotation.PutMapping;
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
		ClientDTO cDTO = convertToDTO(client.getDateOfBirth(), client.getEmail(), client.getIsLoggedIn(), client.getFirstName(), 
									  client.getLastName(), convertToDTOPostings(service.toList(client.getPostings())));
		return cDTO;
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

	@GetMapping(value = {"/posting-applications", "/posting-applications/"})
	public List<ApplicationDTO> getPostingApplications(@RequestParam("owner_email") String owner_email, @RequestParam("posting_date") Date posting_date) throws IllegalArgumentException{
		Posting posting = service.getPosting(owner_email, posting_date);
		List<Application> applications = service.getPostingApplications(posting);
		return convertToDTOApplications(applications);
	}
	
	//TODO: add REST controller method for the get specific application 



	// Alex GET Mappings
	@GetMapping(value = {"/{client}/messages", "{client}/messages/"})
	public List<MessageDTO> getClientMessages(@PathVariable("client") Client client) throws IllegalArgumentException{
		
		List<Message> messages = service.getClientMessages(client);
		return convertToDTOMessage(messages);
		
	}
	
	




	// Nicolas GET Mappings
	
	//Looking at all open Postings
	@GetMapping(value = {"/view-open-postings", "/view-open-postings/"})
	public List<PostingDTO> getOpenPostings() throws IllegalArgumentException{
		
		List <Posting> postings = service.getOpenPostings();
		return convertToDTOPostings(postings);
	}

	//Looking at all Comments on a Posting
	@GetMapping(value = {"/{posting}/comments", "/{posting}/comments/"})
	public List <CommentDTO> getComments(@PathVariable("posting") Posting posting) throws IllegalArgumentException{
		
		List <Comment> comments = service.getComments(posting);
		return convertToDTOComments(comments);
	}

	// Kaustav GET Mappings





	// POST Mappings // 

	// Rahul POST Mappings
	// Creating an account 
	@PostMapping(value = { "/create-account", "/create-account/" }) 
	public ClientDTO registerClient(@RequestParam("email") String email, @RequestParam("firstName") String firstName, 
									@RequestParam("lastName") String lastName, @RequestParam("dob") String dob_string, // Will be in format "yyyy-mm-dd"
									@RequestParam("phoneNumber") String phoneNumber, @RequestParam("address") String address,
									@RequestParam("password") String password) throws IllegalArgumentException, ParseException {
		
		// Changing date to SQL object
        Date dob = Date.valueOf(dob_string);//converting string into sql date  

		Client client = service.createClient(dob, email, password, phoneNumber, 
											 address, firstName, lastName);
											 
		return convertToDTO(client.getEmail(), client.getPassword());
	}

	// Logging out
	@PostMapping(value = { "/logout", "/logout/" })
	public ProfileDTO logoutProfile(@RequestParam("email") String email) {
		if (!service.profileLogout(email).getIsLoggedIn()) {
			return convertToDTO(email, false); 
		} else {
			throw new IllegalArgumentException("Logout failed."); // Really should never happen, but just incase
		}
	}

	// Deleting an account
	@PostMapping(value = { "/delete-account", "/delete-account/" })
	public ProfileDTO deleteAccount(@RequestParam("deleterEmail") String deleterEmail, @RequestParam("deleteeEmail") String deleteeEmail) {
		Client client = service.deleteClient(deleterEmail, deleteeEmail);
		return convertToDTO(client.getEmail(), false);
	}




	// Youssef POST Mappings


	@PostMapping(value = {"/createapplication", "/createapplication"})
	public ApplicationDTO createApplication(@RequestParam("client_email") String client_email, @RequestParam("owner_email") String owner_email, 
			@RequestParam Date posting_date, @RequestParam("homeType") String homeType, @RequestParam("incomeRange") String incomeRange,
			@RequestParam("numberOfResidents") Integer numberOfResidents) throws IllegalArgumentException{
		Client client = service.getClient(client_email);
		Posting posting = service.getPosting(owner_email, posting_date);
		HomeType ht = null;
		IncomeRange ir = null;
		//get enum variable from passed string
		try {
			ht = HomeType.valueOf(homeType);
		} catch (Exception e) {
			throw new IllegalArgumentException(ErrorMessages.invalidHomeType);
		}
		//get enum variable from passed string
		try {
			ir = IncomeRange.valueOf(incomeRange);
		} catch (Exception e) {
			throw new IllegalArgumentException(ErrorMessages.invalidIncomeRange);
		}
		Application application = service.createApplication(client, posting, ht, ir, numberOfResidents);
		return convertToDTO(application);
	}
	
	@PutMapping(value = {"/reject-application", "/reject-application/"})
	public ApplicationDTO rejectApplication(@RequestParam("client_email") String client_email, @RequestParam("owner_email") String owner_email, 
			@RequestParam Date posting_date) throws IllegalArgumentException{
		Posting posting = service.getPosting(owner_email, posting_date);
		Application application = service.getApplication(client_email, posting);
		application = service.rejectApplication(application);
		return convertToDTO(application);
	}
	
	@PutMapping(value = {"/approve-application", "/approve-application/"})
	public ApplicationDTO acceptApplication(@RequestParam("client_email") String client_email, @RequestParam("owner_email") String owner_email, 
			@RequestParam Date posting_date) throws IllegalArgumentException{
		Posting posting = service.getPosting(owner_email, posting_date);
		Application application = service.getApplication(client_email, posting);
		application = service.approveApplication(application);
		return convertToDTO(application);
	}



	// Alex POST Mappings





	// Nicolas POST Mappings

	//Commenting on a Posting
		@PostMapping(value = { "/{posting}/comments", "/{posting}/comments/" })
		public CommentDTO commentOnPost(@PathVariable("posting") @RequestParam("profile") Profile profile, @RequestParam("posting") Posting posting,
				                        @RequestParam("content") String content, @RequestParam("date") String dateString) throws IllegalArgumentException {
			
			
			Date date = Date.valueOf(dateString);
			
			Comment comment = service.commentOnPosting(profile, posting, content, date);
			return convertToDTO(comment);
		}

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

	// For creating an account -- Parameters are used to login
	private ClientDTO convertToDTO(String email, String password) {
		ClientDTO clientDTO = new ClientDTO();
		clientDTO.setEmail(email);
		clientDTO.setPassword(password);
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
