package ca.mcgill.ecse321.petshelter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.petshelter.ErrorMessages;
import ca.mcgill.ecse321.petshelter.dao.ClientRepository;
import ca.mcgill.ecse321.petshelter.model.Client;

@ExtendWith(MockitoExtension.class)
public class UpdateClientProfileServiceTests {

	@Mock
	private ClientRepository clientDAO;

	@InjectMocks
	private PetShelterService service;
	
	private final static String VALID_EMAIL = "john.doe@mail.com";
	private final static String PROFILE_PASSWORD = "ejejojro";
	private final static String FN = "Bob";
	private final static String LN = "Doe";
	private final static String VALID_PHONE_NUMBER = "3234567892";
	private final static String INVALID_PHONE_NUMBER = "514";
	private final static String VALID_ADDRESS = "1234 wolf street";
	private final static Date DOB = Date.valueOf("1999-04-04");
	
	@BeforeEach
	public void setMockOutput() {
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};

		try {
			lenient().when(clientDAO.save(any(Client.class))).thenAnswer(returnParameterAsAnswer); 			
		} catch (Exception e) {
			System.out.println("Exception is being thrown by lenient");
		}
	}
	
	//Test Main Case scenario
	@Test
	public void testUpdateClientProfile() {
		Client client = new Client();
		client.setIsLoggedIn(true);
		try {
			client = service.updateClientProfile(client, PROFILE_PASSWORD, VALID_PHONE_NUMBER, VALID_ADDRESS, FN, LN, DOB);
			assertNotNull(client);
			assertEquals(PROFILE_PASSWORD, client.getPassword());
			assertEquals(VALID_PHONE_NUMBER, client.getPhoneNumber());
			assertEquals(VALID_ADDRESS, client.getAddress());
			assertEquals(FN, client.getFirstName());
			assertEquals(LN, client.getLastName());
			assertEquals(DOB, client.getDateOfBirth());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testUpdateClientProfileNullClient() {
		Client client = null;
		String error = null;
		try {
			client = service.updateClientProfile(client, PROFILE_PASSWORD, VALID_PHONE_NUMBER, VALID_ADDRESS, FN, LN, DOB);
		}catch(Exception e) {
			error = e.getMessage();
		}
		assertNull(client);
		assertEquals(ErrorMessages.accountDoesNotExist, error);
    }
	
	@Test
	public void testUpdateClientProfileNotLoggedIn() {
		Client client = new Client();
		client.setEmail(VALID_EMAIL);
		client.setIsLoggedIn(false);
		String error = null;
		try {
			client = service.updateClientProfile(client, PROFILE_PASSWORD, VALID_PHONE_NUMBER, VALID_ADDRESS, FN, LN, DOB);
		}catch(Exception e) {
			error = e.getMessage();
		}
		assertEquals(ErrorMessages.notLoggedIn, error);	
    }
	
	@Test
	public void testUpdateClientProfileNullAddress() {
		Client client = new Client();
		client.setEmail(VALID_EMAIL);
		client.setIsLoggedIn(true);
		String error = null;
		try {
			client = service.updateClientProfile(client, PROFILE_PASSWORD, VALID_PHONE_NUMBER, null, FN, LN, DOB);
		}catch(Exception e) {
			error = e.getMessage();
		}
		assertEquals(ErrorMessages.invalidAddress, error);	
	}
	
	@Test
	public void testUpdateClientProfileEmptyAddress() {
		Client client = new Client();
		client.setEmail(VALID_EMAIL);
		client.setIsLoggedIn(true);
		String address = "    ";
		String error = null;
		try {
			client = service.updateClientProfile(client, PROFILE_PASSWORD, VALID_PHONE_NUMBER, address, FN, LN, DOB);
		}catch(Exception e) {
			error = e.getMessage();
		}
		assertEquals(ErrorMessages.invalidAddress, error);
	}	
	
	@Test
	public void testUpdateClientProfileNullDOB() {
		Client client = new Client();
		client.setEmail(VALID_EMAIL);
		client.setIsLoggedIn(true);
		String error = null;
		try {
			client = service.updateClientProfile(client, PROFILE_PASSWORD, VALID_PHONE_NUMBER, VALID_ADDRESS, FN, LN, null);
		}catch(Exception e) {
			error = e.getMessage();
		}
		assertEquals(ErrorMessages.invalidDOB, error);
	}
	
	@Test
	public void testUpdateClientProfileNullPassword() {
		Client client = new Client();
		client.setEmail(VALID_EMAIL);
		client.setIsLoggedIn(true);
		String error = null;
		try {
			client = service.updateClientProfile(client, null, VALID_PHONE_NUMBER, VALID_ADDRESS, FN, LN, DOB);
		}catch(Exception e) {
			error = e.getMessage();
		}
		assertEquals(ErrorMessages.invalidPassword, error);
	}
	
	@Test
	public void testUpdateClientProfileShortPassword() {
		Client client = new Client();
		client.setEmail(VALID_EMAIL);
		client.setIsLoggedIn(true);
		String password = "5char"; //password that does not meet the requirements
		String error = null;
		try {
			client = service.updateClientProfile(client, password, VALID_PHONE_NUMBER, VALID_ADDRESS, FN, LN, DOB);
		}catch(Exception e) {
			error = e.getMessage();
		}
		assertEquals(ErrorMessages.invalidPassword, error);
	}
	
	@Test
	public void testUpdateClientProfileNullPhoneNumber() {
		Client client = new Client();
		client.setEmail(VALID_EMAIL);
		client.setIsLoggedIn(true);
		String error = null;
		try {
			client = service.updateClientProfile(client, PROFILE_PASSWORD, null, VALID_ADDRESS, FN, LN, DOB);
		}catch(Exception e) {
			error = e.getMessage();
		}
		assertEquals(ErrorMessages.invalidPhoneNumber, error);
	}
	
	@Test
	public void testUpdateClientProfileInvalidPhoneNumber() {
		Client client = new Client();
		client.setEmail(VALID_EMAIL);
		client.setIsLoggedIn(true);
		String error = null;
		try {
			client = service.updateClientProfile(client, PROFILE_PASSWORD, INVALID_PHONE_NUMBER, VALID_ADDRESS, FN, LN, DOB);
		}catch(Exception e) {
			error = e.getMessage();
		}
		assertEquals(ErrorMessages.invalidPhoneNumber, error);

	}

	@Test
	public void testUpdateClientProfileNullFirstName() {
		Client client = new Client();
		client.setEmail(VALID_EMAIL);
		client.setIsLoggedIn(true);
		String error = null;
		try {
			client = service.updateClientProfile(client, PROFILE_PASSWORD, VALID_PHONE_NUMBER, VALID_ADDRESS, null, LN, DOB);
		}catch(Exception e) {
			error = e.getMessage();
		}
		assertEquals(ErrorMessages.invalidFirstName, error);
	}
	
	@Test
	public void testUpdateClientProfileEmptyFirstName() {
		Client client = new Client();
		client.setEmail(VALID_EMAIL);
		client.setIsLoggedIn(true);
		String firstName = "   ";
		String error = null;
		try {
			client = service.updateClientProfile(client, PROFILE_PASSWORD, VALID_PHONE_NUMBER, VALID_ADDRESS, firstName, LN, DOB);
		}catch(Exception e) {
			error = e.getMessage();
		}
		assertEquals(ErrorMessages.invalidFirstName, error);
	}
	
	@Test
	public void testUpdateClientProfileNullLastName() {
		Client client = new Client();
		client.setEmail(VALID_EMAIL);
		client.setIsLoggedIn(true);
		String error = null;
		try {
			client = service.updateClientProfile(client, PROFILE_PASSWORD, VALID_PHONE_NUMBER, VALID_ADDRESS, FN, null, DOB);
		}catch(Exception e) {
			error = e.getMessage();
		}
		assertEquals(ErrorMessages.invalidLastName, error);
	}
	
	@Test
	public void testUpdateClientProfileEmptyLastName() {
		Client client = new Client();
		client.setEmail(VALID_EMAIL);
		client.setIsLoggedIn(true);
		String lastName = "    ";
		String error = null;
		try {
			client = service.updateClientProfile(client, PROFILE_PASSWORD, VALID_PHONE_NUMBER, VALID_ADDRESS, FN, lastName, DOB);
		}catch(Exception e) {
			error = e.getMessage();
		}
		assertEquals(ErrorMessages.invalidLastName, error);
	}
}
