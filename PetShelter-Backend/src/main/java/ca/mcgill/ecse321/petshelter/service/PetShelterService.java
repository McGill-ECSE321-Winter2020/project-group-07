package ca.mcgill.ecse321.petshelter.service;

import java.sql.Date;
import java.util.ArrayList;

import java.util.Collection;

import java.util.Iterator;

import java.util.List;
import java.util.Set;

// Used for checking for valid email
import java.util.regex.Matcher; 
import java.util.regex.Pattern; 


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.petshelter.model.*;
import ca.mcgill.ecse321.petshelter.dao.*;
import ca.mcgill.ecse321.petshelter.ErrorMessages;;

@Service
public class PetShelterService {

	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private ApplicationRepository applicationRepository;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private CommentRepository commentRepository;	
	@Autowired
	private DonationRepository donationRepository;	
	@Autowired
	private MessageRepository messageRepository;	
	@Autowired
	private PostingRepository postingRepository;
	@Autowired
	private ProfileRepository profileRepository;


	@Transactional
	public Client createClient(Date dob, String email, String password, String phoneNumber, 
							   String address, String firstName, String lastName) {

		// Checking if client exists already
		if (getClient(email) != null) {
			throw new IllegalArgumentException(ErrorMessages.accountExists);
		}

		// Checking if DOB is appropriate -- Need to add check for 18 years of age
		if (dob == null) {
			throw new IllegalArgumentException(ErrorMessages.invalidDOB);
		}

		// Checking if email is appropriate
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
		"[a-zA-Z0-9_+&*-]+)*@" + 
		"(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
		"A-Z]{2,7}$"; 
		Pattern patEmail = Pattern.compile(emailRegex); 
		if (email == null || !(patEmail.matcher(email).matches())) {
			throw new IllegalArgumentException(ErrorMessages.invalidEmail);
		}

		// Checking if password is appropriate -- Longer than 6 chars?
		if (password == null || password.length() < 6) {
			throw new IllegalArgumentException(ErrorMessages.invalidPassword);
		}

		// Checking if phone number is appropriate
		String phoneNumberRegex = "^\\d-\\d-\\d-\\d-\\d-\\d-\\d-\\d-\\d-\\d$";
		Pattern patPhoneNumber = Pattern.compile(phoneNumberRegex); 
		if (phoneNumber == null || !(patPhoneNumber.matcher(phoneNumber).matches())) {
			throw new IllegalArgumentException(ErrorMessages.invalidPhoneNumber);
		}

		// Checking if address is appropriate
		if (address == null || address.equals("")) {
			throw new IllegalArgumentException(ErrorMessages.invalidAddress);
		}
		
		// Checking if names are appropriate
		if (firstName == null || firstName.equals("")) {
			throw new IllegalArgumentException(ErrorMessages.invalidFirstName);
		}
		if (lastName == null || lastName.equals("")) {
			throw new IllegalArgumentException(ErrorMessages.invalidLastName);
		}

		Client client = new Client();

		// Profile attributes
		client.setDateOfBirth(dob);
		client.setEmail(email);
		client.setPassword(password); // Just stored as string for now, can change later
		client.setPhoneNumber(phoneNumber);
		client.setAddress(address);
		client.setPostings(null);
		client.setComments(null);

		// Client attributes
		client.setFirstName(firstName);
		client.setLastName(lastName);
		client.setDonations(null);
		client.setMessages(null);
		client.setApplications(null);

		clientRepository.save(client);
		
		return client;
	}

	@Transactional
	public Client getClient(String email) {
		if (email != null) {
			Client client = clientRepository.findClientByEmail(email);
			if (client == null) {
				throw new IllegalArgumentException(ErrorMessages.accountDoesNotExist);
			}
			return client;
		}
		return null; 
	}


	@Transactional
	public Client deleteClient(String email) {
		if (email != null) {
			Client client = getClient(email);
			clientRepository.delete(client); 
			return client;
		}
		return null; 
	}


	@Transactional
	public boolean clientLogin(String email, String password) {
		if (email != null) {
			Client client = getClient(email);
			if (password.equals(client.getPassword())) { // Change if we end up storing passwords in ciphertext
				return true;
			}
		}
		return false;
	}

	@Transactional
	public Client updateProfile(Client client, String email, String password, String phoneNumber, String address,String firstName, String lastName, Date dob) {
		
		if(!clientRepository.findById(email).equals(client)) {
			throw new IllegalArgumentException("Profile with this email already exists.");
		}
		client.setAddress(address);
		client.setDateOfBirth(dob);
		client.setEmail(email);
		client.setPassword(password);
		client.setPhoneNumber(phoneNumber);
		return client;
	}

	@Transactional
	public Donation sendDonation(Integer amount, Client client, Date date) {

		try {
			if(amount<=0) {
				throw new IllegalArgumentException("Amount needs to be whole and positive number!");
			}
		}
		catch(IllegalArgumentException e) {
			System.out.println("Amount cannot be a letter or a special character!");
		}

		if(date == null) {
			throw new IllegalArgumentException("No date for donation.");
		}

		Donation donation = new Donation();
		donation.setAmount(amount);
		donation.setClient(client);
		donation.setId(client.getEmail().hashCode()*date.hashCode());
		donationRepository.save(donation);
		return donation;
	}

	@Transactional
	public Message sendMessage(Admin admin,Client client,String content,Date date) {
		
		if(content.length() == 0 ) {
			throw new IllegalArgumentException("You need to write a message before sending it.");
		}
		if(content.length() >1000) {
			throw new IllegalArgumentException("Your message is too long.");
		}
		if(date == null) {
			throw new IllegalArgumentException("No date for message.");
		}
		
		//To avoid spam on admin account, checking if content has already been sent as a message.
		java.util.Set<Message> allMess;
		allMess = client.getMessages();
		Iterator<Message> itr = allMess.iterator();
		
		while(itr.hasNext()) {
			if(itr.next().getContent().equalsIgnoreCase(content)) {
				throw new IllegalArgumentException("The message you are trying to send is identical to a previous message already sent.");
			}
		}
		Message message = new Message();
		message.setAdmin(admin);
		message.setClient(client);
		message.setContent(content);
		message.setDate(date);
		message.setId(client.getEmail().hashCode()*date.hashCode());
		messageRepository.save(message);
		return message;

	}


	/**
	 * 
	 * @param profile
	 * @param posting
	 * @param content
	 * @param date
	 * @return comment
	 */


	@Transactional
	public Comment commentOnPosting(Profile profile, Posting posting, String content, Date date) {

		//check inputs are valid
		if(posting == null) {
			throw new IllegalArgumentException(ErrorMessages.invalidPostingComment);
		}
		if(profile == null) {
			throw new IllegalArgumentException(ErrorMessages.invalidProfileComment);
		}
		//check content is not just white spaces
		String contentWhiteSpaceCheck = content.trim();
		if(content == null || contentWhiteSpaceCheck == "" || contentWhiteSpaceCheck == null) {
			throw new IllegalArgumentException(ErrorMessages.invalidContentComment);
		}
		if(date == null) {
			throw new IllegalArgumentException(ErrorMessages.invalidDateComment);
		}


		//create comment object and set all its attributes
		Comment comment = new Comment();
		comment.setPosting(posting);
		comment.setProfile(profile);
		comment.setContent(content);
		comment.setDate(date);
		comment.setId(profile.getEmail().hashCode()*posting.getId()*date.hashCode());

		commentRepository.save(comment);
		return comment;
	}

	@Transactional
	public List<Posting> getOpenPostings(){
		//add comments
		List<Posting> allPostings = toList(postingRepository.findAll());
		List<Posting> openPostings = new ArrayList<Posting>();
		for(Posting posting : allPostings) {
			boolean closedPosting = false;
			for(Application application : posting.getApplication()) {
				if(application.getStatus() == ApplicationStatus.accepted) {
					closedPosting = true;
				}
			}
			if(!closedPosting) {
				openPostings.add(posting);
			}
		}
		return openPostings;
	}

	@Transactional
	public Posting createPosting() {
		return null;
	}

	@Transactional
	public Posting deletePosting() {
		return null;
	}

	@Transactional
	public Posting updatePostingInfo() {
		return null;
	}

	@Transactional
	public List<Application> getPostingApplications(Posting posting){
		//TODO: add checks and warnings, check if posting has another application that was approved
		return toList(posting.getApplication()); //returns ArrayList of applications associated with the posting 
	}

	@Transactional
	public boolean rejectApplication(Application application) {
		//TODO: add checks
		application.setStatus(ApplicationStatus.rejected);
		applicationRepository.save(application);
		return true;
	}

	@Transactional
	public boolean approveApplication(Application application){
		/**
		 * Called when the Profile that made the posting chooses the application that will get the pet advertised in the posting.
		 * Status of this application is changed to approved
		 * Change the status of other applications on the same posting to rejected
		 * The decision is final
		 */
		//TODO: check that this application has a pending status, throw Exception
		//TOD: check that the application is not null
		
		application.setStatus(ApplicationStatus.accepted);
		applicationRepository.save(application);
		
		for(Application a : getPostingApplications(application.getPosting())) {
			if(a != application) {
				a.setStatus(ApplicationStatus.rejected);
				applicationRepository.save(a);
			}
		}
			
		return true;
	}

	@Transactional
	public Application createApplication(Client client, Posting posting, HomeType homeType, IncomeRange incomeRange,Integer numberOfResidents){
		//check client is not null 
		//check posting exists
		//check hometype is not null 
		//check incomerange is not null 
		//check number of residents is more than zero
		
		if(client.equals(posting.getProfile())){
			throw new IllegalArgumentException(ErrorMessages.selfApplication);
		}
		
		Application application = new Application();
		application.setId(client.getEmail().hashCode() * posting.getId());
		application.setClient(client);
		application.setPosting(posting);
		application.setHomeType(homeType);
		application.setIncomeRange(incomeRange);
		application.setNumberOfResidents(numberOfResidents);
		application.setStatus(ApplicationStatus.pending);
		
		applicationRepository.save(application);
		
		return application;
	}
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}
