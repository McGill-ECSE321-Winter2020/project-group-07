package ca.mcgill.ecse321.petshelter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.petshelter.ErrorMessages;
import ca.mcgill.ecse321.petshelter.dao.DonationRepository;
import ca.mcgill.ecse321.petshelter.model.Client;
import ca.mcgill.ecse321.petshelter.model.Donation;

@ExtendWith(MockitoExtension.class)
public class DonationServiceTests {

	@Mock
	private DonationRepository donationDao;

	@InjectMocks
	private PetShelterService service;

	//fields for creating a donation testing.
	private static final Date DATE = Date.valueOf("2000-01-01");
	private static final Integer AMOUNT = 3000;
	private static final String CLIENT_EMAIL = "muffin_man@gmail.com";
	private static final Date DOB = Date.valueOf("1999-10-31");

	//bad arguments for donation
	private static final Integer BAD_AMOUNT = -3000;
	private static final Date FAKE_DATE = Date.valueOf("1999-10-29");

	@BeforeEach
	public void setMockOutput() {
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};

		lenient().when(donationDao.save(any(Donation.class))).thenAnswer(returnParameterAsAnswer);
	}
	
	
	//Test the sendDonation() method
	
	//Test Main Case Scenario 
	@Test
	public void testSendDonation() {
		Client client = new Client();
		client.setEmail(CLIENT_EMAIL);
		client.setIsLoggedIn(true);
		client.setDateOfBirth(DOB);
		Donation donation = null; 
		try {
			donation = service.sendDonation(AMOUNT, client, DATE);
			assertNotNull(donation);
			assertEquals(AMOUNT, donation.getAmount());
			assertEquals(client, donation.getClient());
			assertEquals(DATE, donation.getDate());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testSendDonationNegativeAmount() {
		Client client = new Client();
		client.setIsLoggedIn(true);
		client.setDateOfBirth(DOB);
		Donation donation = null; 
		String error = null;
		try {
			donation = service.sendDonation(BAD_AMOUNT, client, DATE);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(donation);
		assertEquals(ErrorMessages.negAmount, error);
	}
	
	@Test
	public void testSendDonationNullClient() {
		Donation donation = null; 
		String error = null;
		try {
			donation = service.sendDonation(AMOUNT, null, DATE);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(donation);
		assertEquals(ErrorMessages.accountDoesNotExist, error);
	}
	
	@Test
	public void testSendDonationClientLoggedOut() {
		Client client = new Client();
		client.setIsLoggedIn(false);
		client.setDateOfBirth(DOB);
		Donation donation = null; 
		String error = null;
		try {
			donation = service.sendDonation(AMOUNT, client, DATE);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(donation);
		assertEquals(ErrorMessages.notLoggedIn, error);
	}
	
	@Test
	public void testSendDonationClientNullDate() {
		Client client = new Client();
		client.setIsLoggedIn(true);
		client.setDateOfBirth(DOB);
		Donation donation = null; 
		String error = null;
		try {
			donation = service.sendDonation(AMOUNT, client, null);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(donation);
		assertEquals(ErrorMessages.invalidDate, error);
	}
	
	@Test
	public void testSendDonationClientDateBefDOB() {
		Client client = new Client();
		client.setIsLoggedIn(true);
		client.setDateOfBirth(DOB);
		Donation donation = null; 
		String error = null;
		try {
			donation = service.sendDonation(AMOUNT, client, FAKE_DATE);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(donation);
		assertEquals(ErrorMessages.DateBefDOB, error);
	}
	
	//Test the getClientDonations() method
	
	//Test Main Case Scenario
	@Test
	public void testGetClientDonations() {
		Client client = new Client();
		client.setEmail(CLIENT_EMAIL);
		client.setIsLoggedIn(true);
		
		HashSet<Donation> donations = new HashSet<>();
		Donation d1 = new Donation();
		donations.add(d1);
		Donation d2 = new Donation();
		donations.add(d2);
		Donation d3 = new Donation();
		donations.add(d3);
		
		client.setDonations(donations);
		
		List<Donation> returnedDonations = null;
		
		try {
			returnedDonations = service.getClientDonations(client);
			assertNotNull(returnedDonations);
			assertEquals(service.toList(donations), returnedDonations);
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testGetClientDonationsNullClient() {
		List<Donation> donations = null;
		String error = null;
		try {
			donations = service.getClientDonations(null);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(donations);
		assertEquals(ErrorMessages.accountDoesNotExist, error);
	}
	
	@Test
	public void testGetClientDonationsLoggedOutClient() {
		Client client = new Client();
		client.setEmail(CLIENT_EMAIL);
		client.setIsLoggedIn(false);
		List<Donation> donations = null;
		String error = null;
		try {
			donations = service.getClientDonations(client);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(donations);
		assertEquals(ErrorMessages.notLoggedIn, error);
	}
	
}
