package ca.mcgill.ecse321.petshelter.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
	public Profile updateProfile() {
		return null;
	}
	
	@Transactional
	public Donation sendDonation() {
		return null;
	}
	
	@Transactional
	public Message sendMessage() {
		return null;
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
	public List<Application> getPostingApplications(Posting posting){
		//TODO: add checks and warnings, check if posting has another application that was approved
		return toList(posting.getApplication()); //returns ArrayList of applications associated with the posting 
	}
	
	@Transactional
	public boolean approveApplication(Application application){
		/**
		 * Called when the Profile that made the posting chooses the application that will get the pet advertised in the posting.
		 * Status of this application is changed to approved
		 * Change the status of other applications on the same posting to rejected 
		 */
		//TODO: add checks and warnings, check if posting has another application that was approved
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
		//TODO: add checks and warnings
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
