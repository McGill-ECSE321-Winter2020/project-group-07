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
		lenient().when(donationDao.findById(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(DONATION_KEY)) {
				Donation donation = new Donation();
				donation.setId(DONATION_KEY);
				return donation;
			} 
			else {
				return null;
			}
		});
	}

	@Test
	public void testGetNonExistingDonation() {
		Integer Id = NONEXISTING_ID;
		assertNull(service.getDonationbyId(Id));
	}

	@Test
	public void testGetExistingDonation() {
		Integer Id = DONATION_KEY;
		assertNotNull(service.getDonationbyId(Id));
	}

	@Test
	public void testSendDonationWithClientDoesNotExist() {
		String email = CLIENT_EMAIL_NOTEXISTING;
		Integer amount = AMOUNT;
		Date date = DATE;
		assertEquals(null, service.getClient(email));
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
			donation = service.sendDonation(amount, client,date);
			
		} catch(Exception e){
			error = e.getMessage();
			
			
		}
		
		assertNull(donation);
		assertEquals("Amount needs to be whole and positive number!", error);
	}












}
