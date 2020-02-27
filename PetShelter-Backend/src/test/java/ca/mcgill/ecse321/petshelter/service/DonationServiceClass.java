package ca.mcgill.ecse321.petshelter.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.petshelter.dao.*;
import ca.mcgill.ecse321.petshelter.model.*;






@ExtendWith(MockitoExtension.class)
public class DonationServiceClass {



	@Mock
	private DonationRepository donationDao;

	@InjectMocks
	private PetShelterService service;

	private static final Integer DONATION_KEY = 259650;

	//fields for creating a donation testing.
	private static final Date DATE = Date.valueOf("2000-01-01");
	private static final String CLIENT_EMAIL = "muffin_man@gmail.com"; // Testing logged in account
	private static final Integer AMOUNT = 3000;
	
	//test for non existing donation
	private static final String CLIENT_EMAIL_NOTEXISTING = "No.One@gmail.com";
	private static final Integer NONEXISTING_ID = 0000000;

	//fields existing client
	private static final String FIRST_NAME = "John";
	private static final String LAST_NAME = "Doe";
	private static final String CLIENT_EMAIL = "muffin_man@gmail.com";
	private static final String CLIENT_PHONE = "1234567891";
	private static final Date DOB = Date.valueOf("1999-10-31");

	//bad arguments for donation
	private static final Integer BAD_AMOUNT = -3000;
	private static final Date FAKE_DATE = Date.valueOf("1999-10-29");

	@BeforeEach
	public void setMockOutput() {
		lenient().when(service.getDonationbyId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			Donation donation = new Donation();
			Client client = new Client();
			client.setDateOfBirth(DOB);
			client.setEmail(CLIENT_EMAIL);
			client.setFirstName(FIRST_NAME);
			client.setLastName(LAST_NAME);
			donation.setAmount(AMOUNT);
			donation.setClient(client);
			donation.setDate(DATE);
			donation.setId(DONATION_KEY);
			client.setIsLoggedIn(true);
			
			return donation;  
		});
	@BeforeEach
	public void setMockOutput() {
		lenient().when(donationDao.findById(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(DONATION_KEY)) {
				Donation donation = new Donation();
				donation.setId(DONATION_KEY);
				donation.setDate(DOB);
				donation.setAmount(AMOUNT);
				return donation;
			} 
			else {
				return null;
			}
		});
		
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};

		lenient().when(donationDao.save(any(Donation.class))).thenAnswer(returnParameterAsAnswer);

	}

	/**
	 * test to retrieve a donation that doesn't exist.
	 */
	@Test
	public void testGetNonExistingDonation() {
		Integer Id = NONEXISTING_ID;
		assertNull(service.getDonationbyId(Id));
	}


	/**
	 * Test to send a donation and get it.
	 */
	@Test
	public void testGetExistingDonation() {
		Integer Id = DONATION_KEY;
		Integer amount = AMOUNT;
		String fn = FIRST_NAME;
		String ln = LAST_NAME;
		String pn = CLIENT_PHONE;
		Date dob = DOB;
		Date date = DATE;
		String email = CLIENT_EMAIL;
		Client client = new Client();
		client.setDateOfBirth(dob);
		client.setEmail(email);
		client.setFirstName(fn);
		client.setLastName(ln);
		client.setIsLoggedIn(true);
		assertEquals(amount,service.sendDonation(amount,client, date).getAmount());
	}

	/**
	 * test to send a donation with a non-existing client.
	 */
	@Test
	public void testSendDonationWithClientDoesNotExist() {
		String email = CLIENT_EMAIL_NOTEXISTING;
		Integer amount = AMOUNT;
		Date date = DATE;
		Client client = new Client();
		client = null;
		String error = null;
		Donation donation = new Donation();
		donation = null;
		try {
			donation = service.sendDonation(amount, client, date);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(donation);
		assertEquals("Account does not exist.", error);
	}

	/**
	 * test to send a donation with a negative amount.
	 */
	@Test
	public void testSendDonationWithNegativeAmount() {
		Integer amount = BAD_AMOUNT;
		Date date = DOB;
		String email = CLIENT_EMAIL;
		Donation donation = null;
		String error = "";
		try {
			Client client = new Client();
			client.setEmail(email);
			client.setIsLoggedIn(true);
			donation = service.sendDonation(amount, client,date);

		} catch(Exception e){
			error = e.getMessage();


		}

		assertNull(donation);
		assertEquals("Amount needs to be whole and positive number!", error);
	}

	/**
	 * Test to send a donation with a date that is before the dath of birth
	 * of the client.
	 */
	@Test
	public void testSendDonationWrongDate() {
		Integer amount = AMOUNT;
		Date date = FAKE_DATE;
		Date dob = DOB;
		String email = CLIENT_EMAIL;
		Donation donation = null;
		String error = "";
		try {
			Client client = new Client();
			client.setEmail(email);
			client.setDateOfBirth(dob);
			client.setIsLoggedIn(true);
			donation = service.sendDonation(amount, client,date);

		} catch(Exception e){
			error = e.getMessage();


		}

		assertNull(donation);
		assertEquals("The date specified is before the date of birth of the client.", error);
	}

	/**
	 * test for sending a donation if a client is not logged in.
	 */
	@Test
	public void testifClientNotLoggedin() {
		Integer amount = AMOUNT;
		Date date = FAKE_DATE;
		String email = CLIENT_EMAIL;
		Donation donation = null;
		String error = "";
		try {
			Client client = new Client();
			client.setEmail(email);
			client.setIsLoggedIn(false);
			donation = service.sendDonation(amount, client,date);

		} catch(Exception e){
			error = e.getMessage();


		}

		assertNull(donation);
		assertEquals("Account not currently logged in.", error);
	}
}
