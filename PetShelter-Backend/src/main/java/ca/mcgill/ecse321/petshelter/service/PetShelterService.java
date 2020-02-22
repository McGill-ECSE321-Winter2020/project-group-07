package ca.mcgill.ecse321.petshelter.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.petshelter.model.*;
import ca.mcgill.ecse321.petshelter.dao.*;

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

	private java.util.Set<Message> allMess;

	@Transactional
	public Client createClient() {
		return null;
	}

	@Transactional
	public Client deleteClient() {
		return null;
	}

	@Transactional
	public boolean clientLogin() {
		return false;
	}

	@Transactional
	public Profile updateProfile(Profile profile, String email, String password, String phoneNumber, String address,String firstName, String lastName, Date dob) {
		
		if(!profileRepository.findById(email).equals(profile)) {
			throw new IllegalArgumentException("Profile with this email already exists.");
		}
		profile.setAddress(address);
		profile.setDateOfBirth(dob);
		profile.setEmail(email);
		profile.setPassword(password);
		profile.setPhoneNumber(phoneNumber);
		return profile;
	}

	@Transactional
	public Donation sendDonation(Integer amount, Client client, Date date, Integer id) {

		try {
			if(amount<=0) {
				throw new IllegalArgumentException("Amount needs to be whole and positive number!");
			}
		}
		catch(IllegalArgumentException e) {
			System.out.println("Amount cannot be a letter or a special character!");
		}

		if(donationRepository.existsById(id)) {
			throw new IllegalArgumentException("Donation Id already exists.");
		}
		if(date == null) {
			throw new IllegalArgumentException("No date for donation.");
		}

		Donation donation = new Donation();
		donation.setAmount(amount);
		donation.setClient(client);
		donation.setId(id);
		donationRepository.save(donation);
		return donation;
	}

	@Transactional
	public Message sendMessage(Admin admin,Client client,String content,Date date, Integer id) {
		
		if(content.length() == 0 ) {
			throw new IllegalArgumentException("You need to write a message before sending it.");
		}
		if(content.length() >1000) {
			throw new IllegalArgumentException("Your message is too long.");
		}
		if(messageRepository.existsById(id)) {
			throw new IllegalArgumentException("Message id already exists.");
		}
		if(date == null) {
			throw new IllegalArgumentException("No date for message.");
		}
		
		//To avoid spam on admin account, checking if content has already been sent as a message.
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
		message.setId(id);
		messageRepository.save(message);
		return message;

	}

	@Transactional
	public Comment commentOnPosting() {
		return null;
	}

	@Transactional
	public List<Posting> getPostings(){
		return null;
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
	public List<Application> getApplications(){
		return null;
	}

	@Transactional
	public Application selectFinalApplication(){
		return null;
	}

	@Transactional
	public Application createApplication(){
		return null;
	}
}
