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
import java.util.ArrayList;
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
public class AdminServiceTests {

    @Mock
    private ProfileRepository profileDAO;
    
    @InjectMocks
    private PetShelterService service;

    // Dummy profile attributes
    private static final Date PROFILE_DOB = Date.valueOf("2000-01-01");
    private static final String PROFILE_EMAIL_LOGGEDIN = "pet_shelter@petshelter.com"; // Testing logged in account
    private static final String PROFILE_EMAIL_LOGGEDOUT = "pets_r_not_us@gmail.com"; // Testing logged out account
    private static final String PROFILE_PASSWORD = "password1337";
    private static final String PROFILE_PHONENUMBER = "5555555555";
    private static final String PROFILE_ADDRESS = "123 Animal Crossing";
    private static final boolean PROFILE_LOGGEDIN = true; // Testing logged in account
    private static final boolean PROFILE_LOGGEDOUT = false; // Testing logged out account

    // Test stubs
    @BeforeEach
	public void setMockOutput() { 
        // When findProfileByEmail is called in service class, dummy admin object will be returned
		lenient().when(profileDAO.findProfileByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(PROFILE_EMAIL_LOGGEDIN)) {
				Admin admin = new Admin(); // Dummy logged in object 
                admin.setDateOfBirth(PROFILE_DOB);
                admin.setEmail(PROFILE_EMAIL_LOGGEDIN);
                admin.setPassword(PROFILE_PASSWORD);
                admin.setPhoneNumber(PROFILE_PHONENUMBER);
                admin.setAddress(PROFILE_ADDRESS);
                admin.setIsLoggedIn(PROFILE_LOGGEDIN);
				return admin;
            } else if (invocation.getArgument(0).equals(PROFILE_EMAIL_LOGGEDOUT)) {
                Admin admin = new Admin(); // Dummy logged out object 
                admin.setDateOfBirth(PROFILE_DOB);
                admin.setEmail(PROFILE_EMAIL_LOGGEDOUT);
                admin.setPassword(PROFILE_PASSWORD);
                admin.setPhoneNumber(PROFILE_PHONENUMBER);
                admin.setAddress(PROFILE_ADDRESS);
                admin.setIsLoggedIn(PROFILE_LOGGEDOUT);
				return admin;
            } else {
				return null;
			}
        });
        // When calling for all profiles (for getLoggedInUser() method), dummy profiles will be returned
        // This tests for if there's no logged in users. If logged in user --> Tested in ClientServiceTests
        lenient().when(service.toList(profileDAO.findAll())).thenAnswer((InvocationOnMock invocation) -> {
            ArrayList<Profile> allProfiles = new ArrayList<Profile>(); 
            Admin admin = new Admin();
            Client client = new Client(); 
            client.setDateOfBirth(PROFILE_DOB);
            client.setEmail(PROFILE_EMAIL_LOGGEDOUT);
            client.setPassword(PROFILE_PASSWORD);
            client.setPhoneNumber(PROFILE_PHONENUMBER);
            client.setAddress(PROFILE_ADDRESS);
            client.setIsLoggedIn(PROFILE_LOGGEDOUT);
      
            admin.setDateOfBirth(PROFILE_DOB);
            admin.setEmail(PROFILE_EMAIL_LOGGEDIN); // Just a different email, still not actually logged in.
            admin.setPassword(PROFILE_PASSWORD);
            admin.setPhoneNumber(PROFILE_PHONENUMBER);
            admin.setAddress(PROFILE_ADDRESS);
            admin.setIsLoggedIn(PROFILE_LOGGEDOUT); // As shown here.
            
            allProfiles.add(client);
            allProfiles.add(admin); 
            return allProfiles;  
        });
        
        // Whenever the profile is saved, just return the parameter object
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
        };
        
        lenient().when(profileDAO.save(any(Profile.class))).thenAnswer(returnParameterAsAnswer);
	}



    // Loggging in tests
	@Test
	public void testAdminLoginLO() { // Testing if an admin account that's currently logged out can login
		String email = PROFILE_EMAIL_LOGGEDOUT;
        String password = PROFILE_PASSWORD;
		try {
            Admin admin = (Admin) service.profileLogin(email, password);
            assertEquals(true, admin.getIsLoggedIn());
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
    }

    @Test
	public void testAdminLoginLI() { // Testing if an admin account that's currently logged in can login
		String email = PROFILE_EMAIL_LOGGEDIN;
        String password = PROFILE_PASSWORD;
		try {
            Admin admin = (Admin) service.profileLogin(email, password);
		} catch (IllegalArgumentException e) {
            assertEquals(ErrorMessages.loggedIn, e.getMessage());
		}
    }

    @Test
	public void testAdminLoginIP() { // Testing if an admin account with an invalid password can login.
		String email = PROFILE_EMAIL_LOGGEDOUT;
        String password = "this_is_the_wrong_password";
		try {
            Admin admin = (Admin) service.profileLogin(email, password);
		} catch (IllegalArgumentException e) {
            assertEquals(ErrorMessages.invalidPassword, e.getMessage());
		}
    }

    @Test
	public void testAdminLoginEN() { // Testing if an admin account with a null email can login.
		String email = null;
        String password = PROFILE_PASSWORD;
		try {
            Admin admin = (Admin) service.profileLogin(email, password);
		} catch (IllegalArgumentException e) {
            assertEquals(ErrorMessages.accountDoesNotExist, e.getMessage());
		}
    }

    @Test
	public void testAdminLoginPN() { // Testing if an admin account with a null password can login.
		String email = PROFILE_EMAIL_LOGGEDOUT;
        String password = null;
		try {
            Admin admin = (Admin) service.profileLogin(email, password);
		} catch (IllegalArgumentException e) {
            assertEquals(ErrorMessages.accountDoesNotExist, e.getMessage());
		}
    }



    // Logging out tests
    @Test
	public void testAdminLogoutLI() { // Testing if a logged in admin account can logout.
		String email = PROFILE_EMAIL_LOGGEDIN;
		try {
            Admin admin = (Admin) service.profileLogout(email);
            assertEquals(false, admin.getIsLoggedIn()); 
		} catch (IllegalArgumentException e) {
            // Ensuring no other errors occur
            fail();
		}
    }

    @Test
	public void testAdminLogoutLO() { // Testing if an already logged out admin account can logout. 
		String email = PROFILE_EMAIL_LOGGEDOUT;
		try {
            Admin admin = (Admin) service.profileLogout(email);
		} catch (IllegalArgumentException e) {
            assertEquals(ErrorMessages.notLoggedIn, e.getMessage());
		}
    }

    @Test
	public void testAdminLogoutEN() { // Testing if an admin account with a null email can logout.
		String email = null;
		try {
            Admin admin = (Admin) service.profileLogout(email);
		} catch (IllegalArgumentException e) {
            assertEquals(ErrorMessages.accountDoesNotExist, e.getMessage());
		}
    }



    // Testing for getLoggedInUser() if there's no logged in users. Test for logged in user is done in ClientServiceTests.
     @Test
     public void testGetLoggedInUser() {
         try {
             Profile logged_in = service.getLoggedInUser();
         } catch (Exception e) {
             assertEquals(ErrorMessages.notLoggedIn, e.getMessage()); 
         }
     }


}