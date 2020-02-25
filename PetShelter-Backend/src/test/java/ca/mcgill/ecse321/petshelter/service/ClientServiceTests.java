package ca.mcgill.ecse321.petshelter.service;

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


@ExtendWith(MockitoExtension.class)
public class ClientServiceTests {

    @Mock
    private ProfileRepository profileDAO;

    @Mock
    private ClientRepository clientDAO;
    
    @InjectMocks
    private PetShelterService service;

    // Dummy profile attributes
    private static final Date PROFILE_DOB = Date.valueOf("2000-01-01");
    private static final String PROFILE_EMAIL_LOGGEDIN = "muffin_man@gmail.com"; // Testing logged in account
    private static final String PROFILE_EMAIL_LOGGEDOUT = "muffin_woman@gmail.com"; // Testing logged out account
    private static final String PROFILE_PASSWORD = "password1337";
    private static final String PROFILE_PHONENUMBER = "5555555555";
    private static final String PROFILE_ADDRESS = "1729 Drury Lane";
    private static final boolean PROFILE_LOGGEDIN = true; // Testing logged in account
    private static final boolean PROFILE_LOGGEDOUT = false; // Testing logged out account

    // Dummy client attributes
    private static final String CLIENT_FNAME = "Muffin"; 
    private static final String CLIENT_LNAME = "Man";
    private static final String UNREGISTERED_CLIENT_EMAIL = "pet_killer@dogfights.com";

    // Test stubs
    @BeforeEach
	public void setMockOutput() { 
        // When findProfileByEmail is called in service class, dummy client object will be returned
		lenient().when(profileDAO.findProfileByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(PROFILE_EMAIL_LOGGEDIN)) {
				Client client = new Client(); // Dummy logged in object 
                client.setDateOfBirth(PROFILE_DOB);
                client.setEmail(PROFILE_EMAIL_LOGGEDIN);
                client.setPassword(PROFILE_PASSWORD);
                client.setPhoneNumber(PROFILE_PHONENUMBER);
                client.setAddress(PROFILE_ADDRESS);
                client.setIsLoggedIn(PROFILE_LOGGEDIN);
                client.setFirstName(CLIENT_FNAME);
                client.setLastName(CLIENT_LNAME); 
				return client;
            } else if (invocation.getArgument(0).equals(PROFILE_EMAIL_LOGGEDOUT)) {
                Client client = new Client(); // Dummy logged out object 
                client.setDateOfBirth(PROFILE_DOB);
                client.setEmail(PROFILE_EMAIL_LOGGEDOUT);
                client.setPassword(PROFILE_PASSWORD);
                client.setPhoneNumber(PROFILE_PHONENUMBER);
                client.setAddress(PROFILE_ADDRESS);
                client.setIsLoggedIn(PROFILE_LOGGEDOUT);
                client.setFirstName(CLIENT_FNAME);
                client.setLastName(CLIENT_LNAME); 
				return client;
            } else {
				return null;
			}
        });
        // When findClientByEmail is called in service class, dummy client object (or null) will be returned
        lenient().when(clientDAO.findClientByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(PROFILE_EMAIL_LOGGEDIN)) {
				Client client = new Client(); // Dummy logged in object 
                client.setDateOfBirth(PROFILE_DOB);
                client.setEmail(PROFILE_EMAIL_LOGGEDIN);
                client.setPassword(PROFILE_PASSWORD);
                client.setPhoneNumber(PROFILE_PHONENUMBER);
                client.setAddress(PROFILE_ADDRESS);
                client.setIsLoggedIn(PROFILE_LOGGEDIN);
                client.setFirstName(CLIENT_FNAME);
                client.setLastName(CLIENT_LNAME); 
				return client;
            } else if (invocation.getArgument(0).equals(PROFILE_EMAIL_LOGGEDOUT)) {
                Client client = new Client(); // Dummy logged out object 
                client.setDateOfBirth(PROFILE_DOB);
                client.setEmail(PROFILE_EMAIL_LOGGEDOUT);
                client.setPassword(PROFILE_PASSWORD);
                client.setPhoneNumber(PROFILE_PHONENUMBER);
                client.setAddress(PROFILE_ADDRESS);
                client.setIsLoggedIn(PROFILE_LOGGEDOUT);
                client.setFirstName(CLIENT_FNAME);
                client.setLastName(CLIENT_LNAME); 
				return client;
            } else if (invocation.getArgument(0) == null) { // This is a weird one, may be unnecessary.
                return null;
            } else if (invocation.getArgument(0) == UNREGISTERED_CLIENT_EMAIL) {
                return null; 
            } else {
				return null;
			}
		});

        // Whenever the profile is saved, just return the parameter object
        // Technically doesn't matter since the returned object after saving is never used directly
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
        };
        
        lenient().when(profileDAO.save(any(Profile.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(clientDAO.save(any(Client.class))).thenAnswer(returnParameterAsAnswer);           
	}



    // Logging in tests
	@Test
	public void testClientLoginLO() { // Testing if a client account that's currently logged out can login
		String email = PROFILE_EMAIL_LOGGEDOUT;
        String password = PROFILE_PASSWORD;
		try {
            Client client = (Client) service.profileLogin(email, password);
            assertEquals(true, client.getIsLoggedIn());
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
    }

    @Test
	public void testClientLoginLI() { // Testing if a client account that's currently logged in can login
		String email = PROFILE_EMAIL_LOGGEDIN;
        String password = PROFILE_PASSWORD;
		try {
            Client client = (Client) service.profileLogin(email, password);
		} catch (IllegalArgumentException e) {
            assertEquals(ErrorMessages.loggedIn, e.getMessage());
		}
    }

    @Test
	public void testClientLoginIP() { // Testing if a client account with an invalid password can login.
		String email = PROFILE_EMAIL_LOGGEDOUT;
        String password = "this_is_the_wrong_password";
		try {
            Client client = (Client) service.profileLogin(email, password);
		} catch (IllegalArgumentException e) {
            assertEquals(ErrorMessages.invalidPassword, e.getMessage());
		}
    }

    @Test
	public void testClientLoginEN() { // Testing if a client account with a null email can login.
		String email = null;
        String password = PROFILE_PASSWORD;
		try {
            Client client = (Client) service.profileLogin(email, password);
		} catch (IllegalArgumentException e) {
            assertEquals(ErrorMessages.accountDoesNotExist, e.getMessage());
		}
    }

    @Test
	public void testClientLoginPN() { // Testing if a client account with a null password can login.
		String email = PROFILE_EMAIL_LOGGEDOUT;
        String password = null;
		try {
            Client client = (Client) service.profileLogin(email, password);
		} catch (IllegalArgumentException e) {
            assertEquals(ErrorMessages.accountDoesNotExist, e.getMessage());
		}
    }



    // Logging out tests
    @Test
	public void testClientLogoutLI() { // Testing if a logged in client account can logout.
		String email = PROFILE_EMAIL_LOGGEDIN;
		try {
            Client client = (Client) service.profileLogout(email);
            assertEquals(false, client.getIsLoggedIn()); 
		} catch (IllegalArgumentException e) {
            // Ensuring no other errors occur
            fail();
		}
    }

    @Test
	public void testClientLogoutLO() { // Testing if a already logged out client account can logout. 
		String email = PROFILE_EMAIL_LOGGEDOUT;
		try {
            Client client = (Client) service.profileLogout(email);
		} catch (IllegalArgumentException e) {
            assertEquals(ErrorMessages.notLoggedIn, e.getMessage());
		}
    }

    @Test
	public void testClientLogoutEN() { // Testing if a client account with a null email can logout.
		String email = null;
		try {
            Client client = (Client) service.profileLogout(email);
		} catch (IllegalArgumentException e) {
            assertEquals(ErrorMessages.accountDoesNotExist, e.getMessage());
		}
    }
    


    // Get client tests
    @Test
    public void testGetClient() { // Testing if getClient() service method functions with correct inputs.
        String email = PROFILE_EMAIL_LOGGEDIN; // Doesn't really matter if logged in or not
        try {
            Client client = service.getClient(email);
            assertEquals(email, client.getEmail());
        } catch (Exception e) {
            // Checking if any other errors
            fail();
        }
    }

    @Test
    public void testGetClientUR() { // Testing if getClient() service method is called on an unregistered email.
        String email = UNREGISTERED_CLIENT_EMAIL;
        try {
            Client client = service.getClient(email);
        } catch (Exception e) {
            assertEquals(ErrorMessages.accountDoesNotExist, e.getMessage());
        }
    }

    @Test
    public void testGetClientEN() { // Testing if getClient() service method is called on a null email.
        String email = null;
        try {
            Client client = service.getClient(email);
            assertNull(client);
        } catch (Exception e) {
            // Testing for other erroes
            fail();
        }
    }



    // Delete client tests
    @Test
    public void testDeleteClient() { // Testing if a legitimate account can be deleted. 
        String email = PROFILE_EMAIL_LOGGEDIN; // Doesn't really matter if logged in or not
        try {
            Client client = service.deleteClient(email);
            assertEquals(email, client.getEmail());
        } catch (Exception e) {
            // In case of other errors
            fail();
        }
    }

    @Test
    public void testDeleteClientUR() { // Testing if an unregistered user tries to be deleted.
        String email = UNREGISTERED_CLIENT_EMAIL; 
        try {
            Client client = service.deleteClient(email);
        } catch (Exception e) {
            assertEquals(ErrorMessages.accountDoesNotExist, e.getMessage());
        }
    }

    @Test
    public void testDeleteClientEN() { // Testing if a null email tried to be deleted.
        String email = null; 
        try {
            Client client = service.deleteClient(email);
            assertNull(client);
        } catch (Exception e) {
            // In case of other errors
            fail();
        }
    }



    // Create client tests


}