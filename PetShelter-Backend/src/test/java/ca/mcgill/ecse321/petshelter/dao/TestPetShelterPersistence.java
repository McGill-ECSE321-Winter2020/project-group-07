package ca.mcgill.ecse321.petshelter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.petshelter.model.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestPetShelterPersistence {
	
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

	@AfterEach
	public void clearDatabase() {
		adminRepository.deleteAll();
		applicationRepository.deleteAll();
		clientRepository.deleteAll();
		commentRepository.deleteAll();
		donationRepository.deleteAll();
		messageRepository.deleteAll();
		postingRepository.deleteAll();
		profileRepository.deleteAll();
	}

	@Test
	public void testPersistAndLoadAdmin() {
		String email = "john.doe@mail.com";
		// First example for object save/load
		Admin admin = new Admin();
		// First example for attribute save/load
		admin.setEmail(email);
		adminRepository.save(admin);

		admin = null;

		admin = adminRepository.findAdminByEmail(email);
		assertNotNull(admin);
		assertEquals(email, admin.getEmail());
	}


	@Test
	public void testPersistAndLoadApplication() {
		Integer id = 123456;
		// First example for object save/load
		Application application = new Application();
		// First example for attribute save/load
		application.setId(id);
		applicationRepository.save(application);

		application = null;

		application = applicationRepository.findApplicationById(id);
		assertNotNull(application);
		assertEquals(id, application.getId());
	}

	@Test
	public void testPersistAndLoadClient() {
		String email = "john.doe@mail.com";
		// First example for object save/load
		Client client = new Client();
		// First example for attribute save/load
		client.setEmail(email);
		clientRepository.save(client);

		client = null;

		client = clientRepository.findClientByEmail(email);
		assertNotNull(client);
		assertEquals(email, client.getEmail());
	}

	@Test
	public void testPersistAndLoadComment() {
		Integer id = 123456;
		// First example for object save/load
		Comment comment = new Comment();
		// First example for attribute save/load
		comment.setId(id);
		commentRepository.save(comment);

		comment = null;

		comment = commentRepository.findCommentById(id);
		assertNotNull(comment);
		assertEquals(id, comment.getId());
	}

	@Test
	public void testPersistAndLoadDonation() {
		Integer id = 123456;
		// First example for object save/load
		Donation donation = new Donation();
		// First example for attribute save/load
		donation.setId(id);
		donationRepository.save(donation);

		donation = null;

		donation = donationRepository.findDonationById(id);
		assertNotNull(donation);
		assertEquals(id, donation.getId());
	}
	
	@Test
	public void testPersistAndLoadMessage() {
		Integer id = 123456;
		// First example for object save/load
		Message message = new Message();
		// First example for attribute save/load
		message.setId(id);
		messageRepository.save(message);

		message = null;

		message = messageRepository.findMessageById(id);
		assertNotNull(message);
		assertEquals(id, message.getId());
	}
	
	@Test
	public void testPersistAndLoadPosting() {
		Integer id = 123456;
		// First example for object save/load
		Posting posting = new Posting();
		// First example for attribute save/load
		posting.setId(id);
		postingRepository.save(posting);

		posting = null;

		posting = postingRepository.findPostingById(id);
		assertNotNull(posting);
		assertEquals(id, posting.getId());
	}
	
//	@Test
//	public void testPersistAndLoadProfile() {
//		String email = "john.doe@mail.com";
//		// First example for object save/load
//		Profile profile = new Profile();
//		// First example for attribute save/load
//		profile.setEmail(email);
//		profileRepository.save(profile);
//
//		profile = null;
//
//		profile = profileRepository.findProfileByEmail(email);
//		assertNotNull(profile);
//		assertEquals(email, profile.getEmail());
//	}
	
}
