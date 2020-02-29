package ca.mcgill.ecse321.petshelter.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import ca.mcgill.ecse321.petshelter.dao.ClientRepository;
import ca.mcgill.ecse321.petshelter.dao.ProfileRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

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
import ca.mcgill.ecse321.petshelter.ErrorMessages;


public class UpdateClientProfileServiceTests {


	

	@Mock
	private ClientRepository clientDAO;

	@InjectMocks
	private PetShelterService service;
	
	private static String PROFILE_EMAIL_LOGGEDOUT = "john.doe@mail.com";
	private static String PROFILE_PASSWORD = "ejejojro";
	private static String FN = "Bob";
	private static String LN = "Doe";
	private static String PN = "3234567892";
	private static String ADDRESS = "1234 wolf street";
	private static Date DOB = Date.valueOf("1999-04-04");
	
	@BeforeEach
	public void setMockOutput() {
		
		
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};

		lenient().when(clientDAO.save(any(Client.class))).thenAnswer(returnParameterAsAnswer);  
	}
	/**
	 * tests that we cannot update profile if account is not logged in.
	 */
	@Test
	public void testClientLoginLO() { // Testing if a client account that's currently logged out can login
		String email = PROFILE_EMAIL_LOGGEDOUT;
		String add = ADDRESS;
		String pn = PN;
		String ln = LN;
		String fn = FN;
		String pass = PROFILE_PASSWORD;
		Client client = new Client();
		client.setEmail(email);
		client.setIsLoggedIn(false);
		String error = "";
		try {
			service.updateClientProfile( client,pass, pn, add,fn, ln, DOB);
		}catch(Exception e) {
			error = e.getMessage();
		}
		assertEquals("Account not currently logged in.", error);
		
    }
	/**
	 * tests that we cannot update profile if client is null.
	 */
	@Test
	public void testClientIsNull() { // Testing if a client account that's currently logged out can login
		String email = PROFILE_EMAIL_LOGGEDOUT;
		String add = ADDRESS;
		String pn = PN;
		String ln = LN;
		String fn = FN;
		String pass = PROFILE_PASSWORD;
		Client client = new Client();
		client.setEmail(email);
		client.setIsLoggedIn(false);
		client = null;
		String error = "";
		try {
			service.updateClientProfile( client,pass, pn, add,fn, ln, DOB);
		}catch(Exception e) {
			error = e.getMessage();
		}
		assertEquals("Account does not exist.", error);
		
    }
}
