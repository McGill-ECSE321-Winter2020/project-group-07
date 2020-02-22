package ca.mcgill.ecse321.petshelter.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
	public List<Posting> getAllPostings(){

		return toList(postingRepository.findAll());
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

	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}
