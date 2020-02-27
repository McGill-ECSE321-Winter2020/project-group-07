package ca.mcgill.ecse321.petshelter.service;

import java.sql.Date;
import java.util.ArrayList;


import java.util.Collection;

import java.util.Iterator;

import java.util.List;
import java.util.Set;

// Used for validation
import java.util.regex.Matcher; 
import java.util.regex.Pattern; 
import java.util.concurrent.TimeUnit;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.petshelter.model.*;
import ca.mcgill.ecse321.petshelter.dao.*;
import ca.mcgill.ecse321.petshelter.ErrorMessages;

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
		try {
			if (getClient(email) != null) { // This throws doesn't exist exception if client doesn't exist
				throw new IllegalStateException(ErrorMessages.accountExists); // Had to make it a state exception to differ from doesn't exist  
			}
		} catch (IllegalArgumentException e) {
		}

		// Checking if DOB is not null
		if (dob == null) {
			throw new IllegalArgumentException(ErrorMessages.invalidDOB);
		}
		
		// Getting date object of 18 years ago 
		long curr_date_ms = System.currentTimeMillis();
        Date curr_date = new java.sql.Date(curr_date_ms);  
        String curr_date_str = curr_date.toString();
        String monthcurr = new String(new char[] {curr_date_str.toString().charAt(5),curr_date_str.toString().charAt(6)});
        String daycurr = new String(new char[] {curr_date_str.toString().charAt(8),curr_date_str.toString().charAt(9)});
        String str_18y_ago = "2002-" + monthcurr + "-" + daycurr; // Not ideal, may fix later 
        Date dt_18y_ago = Date.valueOf(str_18y_ago); 

		// Checking if 18 years old or over
        int of_age = dob.compareTo(dt_18y_ago); 
        if(of_age > 0) {
            throw new IllegalArgumentException(ErrorMessages.under18);
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
		String phoneNumberRegex = "^[0-9]{10}$";
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

	 /**
	  * Use this function if you need to retrieve a client object.
	  * May need to use try/catch because it throws an exception if no account exists.
	  * @param email
	  * @return Client
	  */
	@Transactional
	public Client getClient(String email) { 
		if (email != null) {
			Client client = clientRepository.findClientByEmail(email);
			if (client == null) {
				throw new IllegalArgumentException(ErrorMessages.accountDoesNotExist);
			}
			return client;
		} else {
			return null; 
		}
	}


	@Transactional
	public Client deleteClient(String deleterEmail, String deleteeEmail) {
		// Checking if person trying to delete is logged in 
		if (deleterEmail == null) {
			return null;
		} else {
			Profile profile = profileRepository.findProfileByEmail(deleterEmail);
			if (profile == null) {
				throw new IllegalArgumentException(ErrorMessages.accountDoesNotExist); 
			}
			if (!profile.getIsLoggedIn()) {
				throw new IllegalArgumentException(ErrorMessages.notLoggedIn);
			}
		}
	
		// Deleting client 
		if (deleteeEmail == null) {
			return null; 
		} else if (deleterEmail.equals(deleteeEmail) || deleterEmail.equals("pet_shelter@petshelter.com")) { // Checking if it's admin or they're deleting themselves
			Client client_to_delete = getClient(deleteeEmail); 
			if (client_to_delete == null) {
				throw new IllegalArgumentException(ErrorMessages.accountDoesNotExist); 
			}
			clientRepository.delete(client_to_delete); // Deleting client
			return client_to_delete;
		} else {
			throw new IllegalArgumentException(ErrorMessages.permissionDenied);
		}		
	}


	@Transactional
	public Profile profileLogin(String email, String password) {

		if (email == null || password == null) {
			throw new IllegalArgumentException(ErrorMessages.accountDoesNotExist);
		}

		Profile profile = profileRepository.findProfileByEmail(email);

		if (profile == null) {
			throw new IllegalArgumentException(ErrorMessages.accountDoesNotExist);
		}

		// Checking if password is correct and account isn't logged in
		if (password.equals(profile.getPassword()) && !profile.getIsLoggedIn()) {
			profile.setIsLoggedIn(true);
			profileRepository.save(profile);
			return profile;
		} else if (profile.getIsLoggedIn()) { 
			throw new IllegalArgumentException(ErrorMessages.loggedIn); 
		} else if (!password.equals(profile.getPassword())) {
			throw new IllegalArgumentException(ErrorMessages.invalidPassword);
		}
		  else {
			return null;
		}
	}

	@Transactional
	public Profile profileLogout(String email) {

		if (email == null) {
			throw new IllegalArgumentException(ErrorMessages.accountDoesNotExist);
		}

		Profile profile = profileRepository.findProfileByEmail(email); 

		if (profile == null) {
			throw new IllegalArgumentException(ErrorMessages.accountDoesNotExist);
		}

		if (!profile.getIsLoggedIn()) {
			throw new IllegalArgumentException(ErrorMessages.notLoggedIn);
		} else {
			profile.setIsLoggedIn(false); 
			profileRepository.save(profile);
			return profile; 
		}

	}

	 /**
	  * Everyone should use this function if they need to retrieve the current active logged in Profile.
	  * If you need to access client attributes, you need to retrieve client using getClient(profile.getEmail());
	  * Using this functions ensures that all of the functions being performed are by a legitimately logged in profile.
	  * Throws an exception if no account is logged in.
	  * @return Profile
	  */
	@Transactional // Everyone should use this 
	public Profile getLoggedInUser(){
		// Get all profiles in database and check which one is logged in 
		List<Profile> allProfiles = toList(profileRepository.findAll());
		for(Profile profile : allProfiles) {
			if (profile.getIsLoggedIn()) {
				return profile; 
			}
		}
		throw new IllegalArgumentException(ErrorMessages.notLoggedIn);
	}

	/**
	 * Method to be used when updating a client profile. From the frontend, the current information will be entered
	 * and the user can modify it and confirm it.
	 * @param client
	 * @param password
	 * @param phoneNumber
	 * @param address
	 * @param firstName
	 * @param lastName
	 * @param dob
	 * @return Client
	 */
	@Transactional
	public Client updateClientProfile(Client client,String password, String phoneNumber, String address,String firstName, String lastName, Date dob) {

		client.setAddress(address);
		client.setDateOfBirth(dob);
		client.setPassword(password);
		client.setPhoneNumber(phoneNumber);
		client.setFirstName(firstName);
		client.setLastName(lastName);
		return client;

	}

	/**
	 * Method to use when a donation is being sent, the amount needs to be an integer.
	 * @param amount
	 * @param client
	 * @param date
	 * @return Donation
	 */
	@Transactional
	public Donation sendDonation(Integer amount, Client client, Date date) {


		try {
			if(amount<=0) {
				throw new IllegalArgumentException(ErrorMessages.negAmount);
			}
		}
		catch(IllegalArgumentException e) {
			System.out.println(ErrorMessages.incorrectCharacter);
		}


		if(date == null) {
			throw new IllegalArgumentException(ErrorMessages.DateDonation);
		}

		Donation donation = new Donation();
		donation.setAmount(amount);
		donation.setClient(client);
		donation.setId(client.getEmail().hashCode()*date.hashCode());
		donationRepository.save(donation);
		return donation;
	}

	/**
	 * Method used when a message is being sent to admin, the content is checked,
	 * if the content is repeated too frequently, the message will not be sent.
	 * To avoid spamming the admin.
	 * @param admin
	 * @param client
	 * @param content
	 * @param date
	 * @return Message
	 */
	@Transactional
	public Message sendMessage(Admin admin,Client client,String content,Date date) {

		if(content.length() == 0 ) {
			throw new IllegalArgumentException(ErrorMessages.NoContent);
		}
		if(content.length() >1000) {
			throw new IllegalArgumentException(ErrorMessages.tooLong);
		}
		if(date == null) {
			throw new IllegalArgumentException(ErrorMessages.dateMessage);
		}

		//To avoid spam on admin account, checking if content has already been sent as a message.
		java.util.Set<Message> allMess;
		allMess = client.getMessages();
		Iterator<Message> itr = allMess.iterator();

		while(itr.hasNext()) {
			Message curr = itr.next();
			String year = new String(new char[] {date.toString().charAt(0),date.toString().charAt(1), date.toString().charAt(2),date.toString().charAt(3)});
			String yearcurr = new String(new char[] {curr.getDate().toString().charAt(0),curr.getDate().toString().charAt(1), curr.getDate().toString().charAt(2),curr.getDate().toString().charAt(3)});
			String month = new String(new char[] {date.toString().charAt(5),date.toString().charAt(6)});
			String monthcurr = new String(new char[] {curr.getDate().toString().charAt(5),curr.getDate().toString().charAt(6)});
			if(curr.getContent().equalsIgnoreCase(content)) {
				if(Math.abs(Integer.parseInt(month)-Integer.parseInt(monthcurr)) <=1 || Math.abs(Integer.parseInt(year)-Integer.parseInt(yearcurr))>=1) {
					throw new IllegalArgumentException(ErrorMessages.MessAlreadyExists);
				}
				
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
	 * This method returns all the messages a client sent since it created its account.
	 * @param client
	 * @return List<Message>, the list of all messages of the client
	 */
	@Transactional
	public List<Message> getClientMessages(Client client){
		
		if(client == null) {
			throw new IllegalArgumentException(ErrorMessages.accountDoesNotExist);
		}
		if(client.getMessages().size() == 0) {
			throw new IllegalArgumentException(ErrorMessages.ClientHasNoMessages);
		}
		return toList(client.getMessages());
		
	}
	
	

	@Transactional
	public Comment commentOnPosting(Profile profile, Posting posting, String content, Date date) {

		//check inputs are valid
		if(posting == null) {
			throw new IllegalArgumentException(ErrorMessages.invalidPosting);
		}
		if(profile == null) {
			throw new IllegalArgumentException(ErrorMessages.invalidProfile);
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
		//get all postings in database and check which ones are still "open" i.e. do not have approved applications 
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
		if(posting == null) {
			throw new IllegalArgumentException(ErrorMessages.invalidPosting);
		}
		return toList(posting.getApplication()); //returns ArrayList of applications associated with the posting 
	}

	@Transactional
	public boolean rejectApplication(Application application) {
		if(application == null) {
			throw new IllegalArgumentException(ErrorMessages.invalidApplication);
		}
		if(application.getStatus() == ApplicationStatus.accepted) {
			throw new IllegalArgumentException(ErrorMessages.rejectingApprovedApp);
		}
		application.setStatus(ApplicationStatus.rejected);
		applicationRepository.save(application);
		return true;
	}

	@Transactional
	public boolean approveApplication(Application application){
		/*
			* Called when the Profile that made the posting chooses the application that will get the pet advertised in the posting.
			* Status of this application is changed to "approved".
			* Change the status of other applications on the same posting to "rejected".
			* The decision is final.
			*/
		if(application == null) {
			throw new IllegalArgumentException(ErrorMessages.invalidApplication);
		}
		if(application.getStatus() != ApplicationStatus.pending) {
			throw new IllegalArgumentException(ErrorMessages.notPendingApp);
		}
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
		if (client == null) {
			throw new IllegalArgumentException(ErrorMessages.accountDoesNotExist);
		}
		if(posting == null) {
			throw new IllegalArgumentException(ErrorMessages.invalidPosting);
		}
		if(client.equals(posting.getProfile())){
			throw new IllegalArgumentException(ErrorMessages.selfApplication);
		}
		if(homeType == null) {
			throw new IllegalArgumentException(ErrorMessages.invalidHomeType);
		}
		if(incomeRange == null) {
			throw new IllegalArgumentException(ErrorMessages.invalidIncomeRange);
		}
		if(numberOfResidents <= 0) {
			throw new IllegalArgumentException(ErrorMessages.invalidNOR);
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

	public <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}
