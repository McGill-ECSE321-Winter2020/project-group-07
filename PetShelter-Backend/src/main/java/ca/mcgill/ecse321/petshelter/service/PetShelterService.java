package ca.mcgill.ecse321.petshelter.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.petshelter.ErrorMessages;
import ca.mcgill.ecse321.petshelter.dao.AdminRepository;
import ca.mcgill.ecse321.petshelter.dao.ApplicationRepository;
import ca.mcgill.ecse321.petshelter.dao.ClientRepository;
import ca.mcgill.ecse321.petshelter.dao.CommentRepository;
import ca.mcgill.ecse321.petshelter.dao.DonationRepository;
import ca.mcgill.ecse321.petshelter.dao.MessageRepository;
import ca.mcgill.ecse321.petshelter.dao.PostingRepository;
import ca.mcgill.ecse321.petshelter.dao.ProfileRepository;
import ca.mcgill.ecse321.petshelter.model.Admin;
import ca.mcgill.ecse321.petshelter.model.Application;
import ca.mcgill.ecse321.petshelter.model.ApplicationStatus;
import ca.mcgill.ecse321.petshelter.model.Client;
import ca.mcgill.ecse321.petshelter.model.Comment;
import ca.mcgill.ecse321.petshelter.model.Donation;
import ca.mcgill.ecse321.petshelter.model.HomeType;
import ca.mcgill.ecse321.petshelter.model.IncomeRange;
import ca.mcgill.ecse321.petshelter.model.Message;
import ca.mcgill.ecse321.petshelter.model.Posting;
import ca.mcgill.ecse321.petshelter.model.Profile;

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
				throw new IllegalStateException(ErrorMessages.accountExists); // Had to make it a state exception to
																				// differ from doesn't exist
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
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";
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
		} else if (deleterEmail.equals(deleteeEmail) || deleterEmail.equals("pet_shelter@petshelter.com")) { // Checking
																												// if
																												// it's
																												// admin
																												// or
																												// they're
																												// deleting
																												// themselves
			Client client_to_delete = getClient(deleteeEmail);
			if (client_to_delete == null) {
				throw new IllegalArgumentException(ErrorMessages.accountDoesNotExist);
			}
			//Delete all the applications, comments, messages, postings
			Set<Application> applications_to_del = client_to_delete.getApplications();
			if(applications_to_del != null) {
				for(Application application: applications_to_del) {
					client_to_delete.removeApplication(application);
					applicationRepository.delete(application);
				}
			}
			
			Set<Comment> comments_to_del = client_to_delete.getComments();
			if(comments_to_del != null) {
				for(Comment comment: comments_to_del) {
					client_to_delete.removeComment(comment);
					commentRepository.delete(comment);
				}				
			}

			Set<Message> messages_to_del = client_to_delete.getMessages();
			if(messages_to_del != null) {
				for(Message message: messages_to_del) {
					client_to_delete.removeMessage(message);
					messageRepository.delete(message);
				}				
			}

			Set<Posting> postings_to_del = client_to_delete.getPostings();
			if(postings_to_del != null) {
				for(Posting posting: postings_to_del) {
					client_to_delete.removePosting(posting);
					postingRepository.delete(posting);
				}				
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
		for (Profile profile : allProfiles) {
			if (profile.getIsLoggedIn()) {
				return profile;
			}
		}
		throw new IllegalArgumentException(ErrorMessages.notLoggedIn);
	}

	/**
	 * Method to be used when updating a client profile. From the frontend, the
	 * current information will be entered and the user can modify it and confirm
	 * it.
	 * 
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
	public Client updateClientProfile(Client client, String password, String phoneNumber, String address, String firstName, String lastName, Date dob) {
		if(client == null) {
			throw new IllegalArgumentException(ErrorMessages.accountDoesNotExist);
		}
		if(!(client.getIsLoggedIn())) {
			throw new IllegalArgumentException(ErrorMessages.notLoggedIn);
		}
		if(address == null || address.trim().length()==0) {
			throw new IllegalArgumentException(ErrorMessages.invalidAddress);
		}
		if(dob == null) {
			throw new IllegalArgumentException(ErrorMessages.invalidDOB);
		}
		if(password == null ||password.trim().length()<=6) {
			throw new IllegalArgumentException(ErrorMessages.invalidPassword);
		}
		String phoneNumberRegex = "^[0-9]{10}$";
		Pattern patPhoneNumber = Pattern.compile(phoneNumberRegex); 
		if (phoneNumber == null || !(patPhoneNumber.matcher(phoneNumber).matches())) {
			throw new IllegalArgumentException(ErrorMessages.invalidPhoneNumber);
		}
		if (firstName == null || firstName.trim().equals("")) {
			throw new IllegalArgumentException(ErrorMessages.invalidFirstName);
		}
		if (lastName == null || lastName.trim().equals("")) {
			throw new IllegalArgumentException(ErrorMessages.invalidLastName);
		}
		
		client.setAddress(address);
		client.setDateOfBirth(dob);
		client.setPassword(password);
		client.setPhoneNumber(phoneNumber);
		client.setFirstName(firstName);
		client.setLastName(lastName);
		client = clientRepository.save(client);
		return client;

	}

	@Transactional
	public Admin getAdmin(String email) {
		if (email != null) {
			Admin admin = adminRepository.findAdminByEmail(email);
			if (admin == null) {
				throw new IllegalArgumentException(ErrorMessages.accountDoesNotExist);
			}
			return admin;
		} else {
			throw new IllegalArgumentException(ErrorMessages.invalidEmail);
		}
	}
	
	
	/**
	 * Method to use when a donation is being sent, the amount needs to be an integer.
	 * @param amount
	 * @param client
	 * @param date
	 * @return Donation
	 * Service method to send a donation.
	 * @param amount
	 * @param client
	 * @param date
	 * @return the donation to be sent
	 */
	@Transactional
	public Donation sendDonation(Integer amount, Client client, Date date) {
		if(amount<=0) {
			throw new IllegalArgumentException(ErrorMessages.negAmount);
		}
		if(client == null) {
			throw new IllegalArgumentException(ErrorMessages.accountDoesNotExist);
		}
		if(!client.getIsLoggedIn()) {
			throw new IllegalArgumentException(ErrorMessages.notLoggedIn);
		}		
		if(date == null) {
			throw new IllegalArgumentException(ErrorMessages.invalidDate);
		}
		if (date.before(client.dateOfBirth)) {
			throw new IllegalArgumentException(ErrorMessages.DateBefDOB);
		}
		Donation donation = new Donation();
		donation.setAmount(amount);
		donation.setClient(client);
		donation.setDate(date);
		donation.setId(client.getEmail().hashCode()*date.hashCode());
		donation = donationRepository.save(donation);
		
		//make sure that referential integrity by also adding the donation to the client 
		client.addDonation(donation);
		client = clientRepository.save(client);			
		
		return donation;
	}
	
	/**
	 * Service method to get all the donations of a client.
	 * @param client
	 * @return list of donation with all the donations of a client
	 */
	@Transactional
	public List<Donation> getClientDonations(Client client){
		if(client == null) {
			throw new IllegalArgumentException(ErrorMessages.accountDoesNotExist);
		}
		if(!client.getIsLoggedIn()) {
			throw new IllegalArgumentException(ErrorMessages.notLoggedIn);
		}
		return toList(client.getDonations());
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
	 * @return message to be sent
	 */
	@Transactional
	public Message sendMessage(Admin admin, Client client, String content, Date date) {
		if(content == null || content.trim().length() == 0 ) {
			throw new IllegalArgumentException(ErrorMessages.NoContent);
		}
		if(content.length() > 1000) {
			throw new IllegalArgumentException(ErrorMessages.tooLong);
		}
		if(date == null) {
			throw new IllegalArgumentException(ErrorMessages.invalidDate);
		}
		if(client == null) {
			throw new IllegalArgumentException(ErrorMessages.accountDoesNotExist);
		}
		if(!client.getIsLoggedIn()) {
			throw new IllegalArgumentException(ErrorMessages.notLoggedIn);
		}
		if(admin == null) {
			throw new IllegalArgumentException(ErrorMessages.IncorrectAdmin);
		}
		if(date.before(client.dateOfBirth)) {
			throw new IllegalArgumentException(ErrorMessages.DateBefDOB);
		}

		Message message = new Message();
		message.setAdmin(admin);
		admin.addMessage(message);
		message.setClient(client);
		client.addMessage(message);
		message.setContent(content);
		message.setDate(date);
		message.setId(client.getEmail().hashCode() * date.hashCode());
		
		message = messageRepository.save(message);
		admin = adminRepository.save(admin);
		client = clientRepository.save(client);
		return message;
	}

	/**
	 * This method returns all the messages a client sent since it created its
	 * account.
	 * 
	 * @param client
	 * @return List<Message>, the list of all messages of the client
	 */
	@Transactional
	public List<Message> getClientMessages(Client client){
		if(client == null) {
			throw new IllegalArgumentException(ErrorMessages.accountDoesNotExist);
		}
		if(!client.getIsLoggedIn()) {
			throw new IllegalArgumentException(ErrorMessages.notLoggedIn);
		}
		return toList(client.getMessages());
	}
	
	@Transactional
	public List<Message> getAdminMessages(Admin admin){
		if(admin == null) {
			throw new IllegalArgumentException(ErrorMessages.accountDoesNotExist);
		}
		if(!admin.getIsLoggedIn()) {
			throw new IllegalArgumentException(ErrorMessages.notLoggedIn);
		}
		return toList(admin.getMessages());
	}


	@Transactional
	public Comment commentOnPosting(Profile profile, Posting posting, String content, Date date) {

		// check inputs are valid
		if (posting == null) {
			throw new IllegalArgumentException(ErrorMessages.invalidPosting);
		}
		if (profile == null) {
			throw new IllegalArgumentException(ErrorMessages.invalidProfile);
		}
		if (!profile.getIsLoggedIn()) {
			throw new IllegalArgumentException(ErrorMessages.invalidProfileNotLoggedIn);
		}
		// check content is not just white spaces
		String contentWhiteSpaceCheck = content.trim();
		if (content == null || contentWhiteSpaceCheck == "" || contentWhiteSpaceCheck == null) {
			throw new IllegalArgumentException(ErrorMessages.invalidContentComment);
		}
		if (date == null) {
			throw new IllegalArgumentException(ErrorMessages.invalidDateComment);
		}
		if (date.before(profile.getDateOfBirth())) {
			throw new IllegalArgumentException(ErrorMessages.invalidDateCommentProfile);
		}
		if (date.before(posting.getDate())) {
			throw new IllegalArgumentException(ErrorMessages.invalidDateCommentPosting);
		}

		// create comment object and set all its attributes
		Comment comment = new Comment();
		comment.setPosting(posting);
		posting.addComment(comment);
		comment.setProfile(profile);
		profile.addComment(comment);
		comment.setContent(content);
		comment.setDate(date);
		comment.setId(profile.getEmail().hashCode() * posting.getId() * date.hashCode());
		
		comment = commentRepository.save(comment);
		posting = postingRepository.save(posting);
		profile = profileRepository.save(profile);
		
		return comment;
	}

	@Transactional
	public List<Comment> getComments(Posting posting) {
		if (posting == null) {
			throw new IllegalArgumentException(ErrorMessages.invalidPosting);
		}
		List<Comment> allComments = toList(commentRepository.findAll());
		List<Comment> comments = new ArrayList<Comment>();
		for (Comment comment : allComments) {
			if (comment.getPosting().equals(posting)) {
				String contentWhiteSpaceCheck = comment.getContent().trim();
				if (comment.getContent() == null || contentWhiteSpaceCheck == "" || contentWhiteSpaceCheck == null) {
					throw new IllegalArgumentException(ErrorMessages.invalidContentComment);
				}
				if (comment.getDate() == null) {
					throw new IllegalArgumentException(ErrorMessages.invalidDateComment);
				}
				if (comment.getDate().before(comment.getProfile().getDateOfBirth())) {
					throw new IllegalArgumentException(ErrorMessages.invalidDateCommentProfile);
				}
				if (comment.getDate().before(comment.getPosting().getDate())) {
					throw new IllegalArgumentException(ErrorMessages.invalidDateCommentPosting);
				}

				//add only valid comments that are on that posting

				comments.add(comment);
			}
		}
		return comments;
	}

	@Transactional
	public List<Posting> getOpenPostings() {
		// get all postings in database and check which ones are still "open" i.e. do
		// not have approved applications
		List<Posting> allPostings = toList(postingRepository.findAll());
		List<Posting> openPostings = new ArrayList<Posting>();
		for (Posting posting : allPostings) {
			boolean closedPosting = false;
			for (Application application : posting.getApplication()) {
				if (application.getStatus() == ApplicationStatus.accepted) {
					closedPosting = true;
				}
			}
			if (!closedPosting) {
				openPostings.add(posting);
			}
		}
		return openPostings;
	}

	/*
	 * post update delete a listing for adoption which includes
	 */

	@Transactional
	public Posting createPosting(Profile profile, Date postDate, String petName, Date dob, String breed, String picture,
			String reason) {
		if (profile == null) {
			throw new IllegalArgumentException(ErrorMessages.invalidProfile);
		}
		if (postDate == null) {
			throw new IllegalArgumentException(ErrorMessages.invalidDate);
		}
		if (petName == null || petName.replaceAll("\\s+","").equals("") || !petName.matches("^[a-zA-Z]*$")) {
			throw new IllegalArgumentException(ErrorMessages.invalidPetName);
		}
		Date rightNow = new Date(System.currentTimeMillis());
		if (dob == null || dob.compareTo(rightNow) >= 0) {
			throw new IllegalArgumentException(ErrorMessages.invalidPetDOB);
		}
		if (breed == null || breed.replaceAll("\\s+","").equals("") || !breed.matches("^[a-zA-Z]*$")) {
			throw new IllegalArgumentException(ErrorMessages.invalidBreed);
		}
		if (picture == null || picture.replaceAll("\\s+","").equals("")) {
			throw new IllegalArgumentException(ErrorMessages.invalidPicture);
		}
		if (reason == null || reason.replaceAll("\\s+","").equals("") || reason.length() >= 1000) {
			throw new IllegalArgumentException(ErrorMessages.invalidReason);
		}
		if (!profile.getIsLoggedIn()) {
			throw new IllegalArgumentException(ErrorMessages.invalidLoggedIn);
		}

		Posting posting = new Posting();
		posting.setProfile(profile);
		profile.addPosting(posting);
		posting.setComment(null);
		posting.setApplication(null);
		posting.setDate(postDate);
		posting.setPetName(petName);
		posting.setPetDateOfBirth(dob);
		posting.setPetBreed(breed);
		posting.setPicture(picture);
		posting.setDescription(reason);
		posting.setId(profile.getEmail().hashCode() * postDate.hashCode());
		
		posting = postingRepository.save(posting);
		profile = profileRepository.save(profile);
		
		return posting;
	}

	@Transactional
	public Posting deletePosting(Posting posting) {
		if (posting == null) {
			throw new IllegalArgumentException(ErrorMessages.invalidPosting);
		}
		if (!posting.getProfile().getIsLoggedIn()) {
			throw new IllegalArgumentException(ErrorMessages.invalidLoggedIn);
		}

		//delete associated applications and comments first
		Set<Application> applications_to_del = posting.getApplication();
		if(applications_to_del != null) {
			for(Application application: applications_to_del) {
				posting.removeApplication(application);
				applicationRepository.delete(application);
			}
		}
		
		Set<Comment> comments_to_del = posting.getComment();
		if(comments_to_del != null) {
			for(Comment comment: comments_to_del) {
				posting.removeComment(comment);
				commentRepository.delete(comment);
			}				
		}

		
		postingRepository.delete(posting);
		return posting;

	}

	@Transactional
	public Posting updatePostingInfo(Posting posting, String petName, Date dob, String breed, String picture,
			String reason) {
		if (posting == null) {
			throw new IllegalArgumentException(ErrorMessages.invalidPosting);
		}
		if (petName == null || petName.replaceAll("\\s+","").equals("") || !petName.matches("^[a-zA-Z]*$")) {
			throw new IllegalArgumentException(ErrorMessages.invalidPetName);
		}
		Date rightNow = new Date(System.currentTimeMillis());
		if (dob == null || dob.compareTo(rightNow) >= 0) {
			throw new IllegalArgumentException(ErrorMessages.invalidPetDOB);
		}
		if (breed == null || breed.replaceAll("\\s+","").equals("") || !breed.matches("^[a-zA-Z]*$")) {
			throw new IllegalArgumentException(ErrorMessages.invalidBreed);
		}
		if (picture == null || picture.replaceAll("\\s+","").equals("")) {
			throw new IllegalArgumentException(ErrorMessages.invalidPicture);
		}
		if (reason == null || reason.replaceAll("\\s+","").equals("") || reason.length() >= 1000) {
			throw new IllegalArgumentException(ErrorMessages.invalidReason);
		}
		if (!posting.getProfile().getIsLoggedIn()) {
			throw new IllegalArgumentException(ErrorMessages.invalidLoggedIn);
		}
		

		posting.setPetName(petName);
		posting.setPetDateOfBirth(dob);
		posting.setPetBreed(breed);
		posting.setPicture(picture);
		posting.setDescription(reason);
		posting = postingRepository.save(posting);
		return posting;
	}

	@Transactional
	public Posting getPosting(String email, Date date) {
		if (email == null) {
			throw new IllegalArgumentException(ErrorMessages.invalidEmail);
		}
		if (date == null) {
			throw new IllegalArgumentException(ErrorMessages.invalidDate);
		}

		Posting posting = postingRepository.findPostingById(email.hashCode() * date.hashCode());
		if (posting == null) {
			throw new IllegalArgumentException(ErrorMessages.postingDoesNotExist);
		}
		return posting;
	}

	@Transactional
	public Application getApplication(String applicant_email, Posting posting) {
		if (applicant_email == null) {
			throw new IllegalArgumentException(ErrorMessages.invalidEmail);
		}
		if (posting == null) {
			throw new IllegalArgumentException(ErrorMessages.invalidPosting);
		}

		Application application = applicationRepository
				.findApplicationById(applicant_email.hashCode() * posting.hashCode());
		if (application == null) {
			throw new IllegalArgumentException(ErrorMessages.applicationDoesNotExist);
		}
		return application;
	}

	public List<Application> getPostingApplications(Posting posting) {
		if (posting == null) {
			throw new IllegalArgumentException(ErrorMessages.invalidPosting);
		}
		return toList(posting.getApplication()); // returns ArrayList of applications associated with the posting
	}

	@Transactional
	public Application rejectApplication(Application application) {
		if (application == null) {
			throw new IllegalArgumentException(ErrorMessages.invalidApplication);
		}
		if (application.getStatus() == ApplicationStatus.accepted) {
			throw new IllegalArgumentException(ErrorMessages.rejectingApprovedApp);
		}
		application.setStatus(ApplicationStatus.rejected);
		application = applicationRepository.save(application);
		return application;
	}

	@Transactional
	public Application approveApplication(Application application) {
		/*
		 * Called when the Profile that made the posting chooses the application that
		 * will get the pet advertised in the posting. Status of this application is
		 * changed to "approved". Change the status of other applications on the same
		 * posting to "rejected". The decision is final.
		 */
		if (application == null) {
			throw new IllegalArgumentException(ErrorMessages.invalidApplication);
		}
		if (application.getStatus() != ApplicationStatus.pending) {
			throw new IllegalArgumentException(ErrorMessages.notPendingApp);
		}
		application.setStatus(ApplicationStatus.accepted);
		applicationRepository.save(application);
		application = applicationRepository.save(application);

		for (Application a : getPostingApplications(application.getPosting())) {
			if (!(a.equals(application))) {
				a.setStatus(ApplicationStatus.rejected);
				applicationRepository.save(a);
			}
		}

		return application;
	}

	@Transactional
	public Application createApplication(Client client, Posting posting, HomeType homeType, IncomeRange incomeRange,
			Integer numberOfResidents) {
		if (client == null) {
			throw new IllegalArgumentException(ErrorMessages.accountDoesNotExist);
		}
		if (posting == null) {
			throw new IllegalArgumentException(ErrorMessages.invalidPosting);
		}
		if (client.getEmail().equals(posting.getProfile().getEmail())) {
			throw new IllegalArgumentException(ErrorMessages.selfApplication);
		}
		if (homeType == null) {
			throw new IllegalArgumentException(ErrorMessages.invalidHomeType);
		}
		if (incomeRange == null) {
			throw new IllegalArgumentException(ErrorMessages.invalidIncomeRange);
		}
		if (numberOfResidents <= 0) {
			throw new IllegalArgumentException(ErrorMessages.invalidNOR);
		}

		Application application = new Application();
		application.setId(client.getEmail().hashCode() * posting.getId());
		application.setClient(client);
		client.addApplication(application);
		application.setPosting(posting);
		posting.addApplication(application);
		application.setHomeType(homeType);
		application.setIncomeRange(incomeRange);
		application.setNumberOfResidents(numberOfResidents);
		application.setStatus(ApplicationStatus.pending);
		
		application = applicationRepository.save(application);
		posting = postingRepository.save(posting);
		client = clientRepository.save(client);
		
		return application;
	}

	public <T> List<T> toList(Iterable<T> iterable) {
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}
