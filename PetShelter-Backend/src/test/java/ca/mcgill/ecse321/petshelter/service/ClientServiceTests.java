package ca.mcgill.ecse321.petshelter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.sql.Date;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.petshelter.ErrorMessages;
import ca.mcgill.ecse321.petshelter.dao.ApplicationRepository;
import ca.mcgill.ecse321.petshelter.dao.ClientRepository;
import ca.mcgill.ecse321.petshelter.dao.CommentRepository;
import ca.mcgill.ecse321.petshelter.dao.MessageRepository;
import ca.mcgill.ecse321.petshelter.dao.PostingRepository;
import ca.mcgill.ecse321.petshelter.dao.ProfileRepository;
import ca.mcgill.ecse321.petshelter.model.Admin;
import ca.mcgill.ecse321.petshelter.model.Application;
import ca.mcgill.ecse321.petshelter.model.Client;
import ca.mcgill.ecse321.petshelter.model.Comment;
import ca.mcgill.ecse321.petshelter.model.Message;
import ca.mcgill.ecse321.petshelter.model.Posting;
import ca.mcgill.ecse321.petshelter.model.Profile;


@ExtendWith(MockitoExtension.class)
public class ClientServiceTests {

    @Mock
    private ProfileRepository profileDAO;

    @Mock
    private ClientRepository clientDAO;
    
    @Mock
    private ApplicationRepository applicationDAO;
    
    @Mock
    private MessageRepository messageDAO;
    
    @Mock
    private CommentRepository commentDAO;
    
    @Mock
    private PostingRepository postingDAO;
    
    @InjectMocks
    private PetShelterService service;

    // Dummy profile attributes
    private static final Date PROFILE_DOB = Date.valueOf("2000-01-01");
    private static final String PROFILE_EMAIL_LOGGEDIN = "muffin_man@gmail.com"; // Testing logged in account
    private static final String PROFILE_EMAIL_LOGGEDOUT = "muffin_woman@gmail.com"; // Testing logged out account
    private static final String ADMIN_EMAIL = "pet_shelter@petshelter.com"; // Testing admin account
    private static final String PROFILE_PASSWORD = "password1337";
    private static final String PROFILE_PHONENUMBER = "5555555555";
    private static final String PROFILE_ADDRESS = "1729 Drury Lane";
    private static final boolean PROFILE_LOGGEDIN = true; // Testing logged in account
    private static final boolean PROFILE_LOGGEDOUT = false; // Testing logged out account

    // Dummy client attributes
    private static final String CLIENT_FNAME = "Muffin"; 
    private static final String CLIENT_LNAME = "Man";
    private static final String UNREGISTERED_CLIENT_EMAIL = "pet_killer@dogfights.com";

    // Dummy illegitimate attributes for creating a new client
    private static final Date UNDERAGE_DOB = Date.valueOf("2010-01-01");
    private static final String EMAIL_DOT = "dot_before_atsign.@gmail.com";
    private static final String EMAIL_NO_AT = "no_at_sign_at_gmail.com";
    private static final String EMAIL_SYMB_DN = "symbol_in_domain@gmai!.com";
    private static final String EMAIL_NO_DOT = "no_dot_after@gmailcom";
    private static final String EMAIL_1LETTER_DOMAIN = "one_letter_tld@gmail.c";
    private static final String EMAIL_8LETTER_DOMAIN = "eight_letter_tld@gmail.eletters";
    private static final String FEW_CHAR_PASS = "5char";
    private static final String LONG_PHONENUMBER = "1234567891011";
    private static final String NON_NUMERIC_PHONENUMBER = "NotNumbers";


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
            } else if (invocation.getArgument(0).equals(ADMIN_EMAIL)) {
                Admin admin = new Admin(); // Dummy logged in admin account.
                admin.setDateOfBirth(PROFILE_DOB);
                admin.setEmail(PROFILE_EMAIL_LOGGEDIN);
                admin.setPassword(PROFILE_PASSWORD);
                admin.setPhoneNumber(PROFILE_PHONENUMBER);
                admin.setAddress(PROFILE_ADDRESS);
                admin.setIsLoggedIn(PROFILE_LOGGEDIN); 
                return admin;
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
        // When calling for all profiles (for getLoggedInUser() method), dummy profiles will be returned
        // This tests for if there's a logged in user at all. If no logged in user --> Tested in AdminServiceTests
        lenient().when(service.toList(profileDAO.findAll())).thenAnswer((InvocationOnMock invocation) -> {
            ArrayList<Profile> allProfiles = new ArrayList<Profile>(); 
            Client client1 = new Client();
            Client client2 = new Client(); 
            client1.setDateOfBirth(PROFILE_DOB);
            client1.setEmail(PROFILE_EMAIL_LOGGEDOUT);
            client1.setPassword(PROFILE_PASSWORD);
            client1.setPhoneNumber(PROFILE_PHONENUMBER);
            client1.setAddress(PROFILE_ADDRESS);
            client1.setIsLoggedIn(PROFILE_LOGGEDOUT);
            client1.setFirstName(CLIENT_FNAME);
            client1.setLastName(CLIENT_LNAME);
            client2.setDateOfBirth(PROFILE_DOB);
            client2.setEmail(PROFILE_EMAIL_LOGGEDIN);
            client2.setPassword(PROFILE_PASSWORD);
            client2.setPhoneNumber(PROFILE_PHONENUMBER);
            client2.setAddress(PROFILE_ADDRESS);
            client2.setIsLoggedIn(PROFILE_LOGGEDIN);
            client2.setFirstName(CLIENT_FNAME);
            client2.setLastName(CLIENT_LNAME);
            allProfiles.add(client1);
            allProfiles.add(client2); 
            return allProfiles;  
        });

        // Whenever the profile is saved, just return the parameter object
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
        };
        
        lenient().when(profileDAO.save(any(Profile.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(clientDAO.save(any(Client.class))).thenAnswer(returnParameterAsAnswer);
        lenient().doNothing().when(clientDAO).delete(any(Client.class));
        lenient().doNothing().when(applicationDAO).delete(any(Application.class));
        lenient().doNothing().when(commentDAO).delete(any(Comment.class));
        lenient().doNothing().when(messageDAO).delete(any(Message.class));
        lenient().doNothing().when(postingDAO).delete(any(Posting.class));
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
	public void testClientLoginUR() { // Testing if an unregistered client account can login.
		String email = UNREGISTERED_CLIENT_EMAIL;
        String password = PROFILE_PASSWORD;
		try {
            Client client = (Client) service.profileLogin(email, password);
		} catch (IllegalArgumentException e) {
            assertEquals(ErrorMessages.accountDoesNotExist, e.getMessage());
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
	public void testClientLogoutLO() { // Testing if an already logged out client account can logout. 
		String email = PROFILE_EMAIL_LOGGEDOUT;
		try {
            Client client = (Client) service.profileLogout(email);
		} catch (IllegalArgumentException e) {
            assertEquals(ErrorMessages.notLoggedIn, e.getMessage());
		}
    }

    @Test
	public void testClientLogoutUR() { // Testing if an unregistered user can logout. 
		String email = UNREGISTERED_CLIENT_EMAIL;
		try {
            Client client = (Client) service.profileLogout(email);
		} catch (IllegalArgumentException e) {
            assertEquals(ErrorMessages.accountDoesNotExist, e.getMessage());
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



    // Get logged in user test -- Test for if there is one logged in user. If none logged in --> Tested in AdminServiceTests.java
    @Test
    public void testGetLoggedInUser() {
        try {
            Profile logged_in = service.getLoggedInUser();
            assertEquals(PROFILE_EMAIL_LOGGEDIN, logged_in.getEmail());
        } catch (Exception e) {
            fail();
        }
    }



    // Delete client tests
    @Test
    public void testDeleteClient() { // Testing if a legitimate, logged in client can delete their own account. 
        String email = PROFILE_EMAIL_LOGGEDIN; 
        try {
            Client client = service.deleteClient(email, email);
            assertEquals(email, client.getEmail());
        } catch (Exception e) {
            // In case of other errors
            fail();
        }
    }

    @Test
    public void testDeleteClientLO() { // Testing if a logged out client can delete their account. 
        String email = PROFILE_EMAIL_LOGGEDOUT; 
        try {
            Client client = service.deleteClient(email, email);
        } catch (Exception e) {
            assertEquals(ErrorMessages.notLoggedIn, e.getMessage());
        }
    }

    @Test
    public void testAdminDeleteClient() { // Testing if logged in admin can delete a client account. 
        String deleterEmail = ADMIN_EMAIL;
        String deleteeEmail = PROFILE_EMAIL_LOGGEDOUT; 
        try {
            Client client = service.deleteClient(deleterEmail, deleteeEmail);
            assertEquals(deleteeEmail, client.getEmail());
        } catch (Exception e) {
            // In case of other errors
            fail();
        }
    }

    @Test
    public void testDeleteAnotherClient() { // Testing if a client can delete another client's account. 
        String deleterEmail = PROFILE_EMAIL_LOGGEDIN;
        String deleteeEmail = PROFILE_EMAIL_LOGGEDOUT;
        try {
            Client client = service.deleteClient(deleterEmail, deleteeEmail);
        } catch (Exception e) {
            assertEquals(ErrorMessages.permissionDenied, e.getMessage());
        }
    }

    @Test
    public void testDeleteClientUR() { // Testing if an unregistered user tries to be deleted.
        String email = UNREGISTERED_CLIENT_EMAIL; 
        try {
            Client client = service.deleteClient(email, email);
        } catch (Exception e) {
            assertEquals(ErrorMessages.accountDoesNotExist, e.getMessage());
        }
    }

    @Test
    public void testDeleteClientEN() { // Testing if a null email tried to be deleted.
        String email = null; 
        try {
            Client client = service.deleteClient(email, email);
            assertNull(client);
        } catch (Exception e) {
            // In case of other errors
            fail();
        }
    }



    // Create client tests
    @Test
    public void testCreateClient() { // Testing if a valid client with all valid attributes can be created
        try {
            Client client = service.createClient(PROFILE_DOB, UNREGISTERED_CLIENT_EMAIL, PROFILE_PASSWORD, 
                                                PROFILE_PHONENUMBER, PROFILE_ADDRESS, CLIENT_FNAME, CLIENT_LNAME);
            assertEquals(UNREGISTERED_CLIENT_EMAIL, client.getEmail()); 
        } catch (Exception e) {
            // In case of other errors
            fail(); 
        }
    }

    @Test
    public void testCreateClientAR() { // Testing if an already registered client can be created
        try {
            Client client = service.createClient(PROFILE_DOB, PROFILE_EMAIL_LOGGEDOUT, PROFILE_PASSWORD, 
                                                PROFILE_PHONENUMBER, PROFILE_ADDRESS, CLIENT_FNAME, CLIENT_LNAME);
        } catch (Exception e) {
            assertEquals(ErrorMessages.accountExists, e.getMessage()); 
        }
    }

    @Test
    public void testCreateClientND() { // Testing if a client with a null DOB can be created
        try {
            Client client = service.createClient(null, UNREGISTERED_CLIENT_EMAIL, PROFILE_PASSWORD, 
                                                PROFILE_PHONENUMBER, PROFILE_ADDRESS, CLIENT_FNAME, CLIENT_LNAME);
        } catch (Exception e) {
            assertEquals(ErrorMessages.invalidDOB, e.getMessage()); 
        }
    }

    @Test
    public void testCreateClientUA() { // Testing if an underage (under 18) client can be created
        try {
            Client client = service.createClient(UNDERAGE_DOB, UNREGISTERED_CLIENT_EMAIL, PROFILE_PASSWORD, 
                                                PROFILE_PHONENUMBER, PROFILE_ADDRESS, CLIENT_FNAME, CLIENT_LNAME);
        } catch (Exception e) {
            assertEquals(ErrorMessages.under18, e.getMessage()); 
        }
    }
    
    @Test
    public void testCreateClientNE() { // Testing if a null email can be used to create an account
        try {
            Client client = service.createClient(PROFILE_DOB, null, PROFILE_PASSWORD, 
                                                PROFILE_PHONENUMBER, PROFILE_ADDRESS, CLIENT_FNAME, CLIENT_LNAME);
        } catch (Exception e) {
            assertEquals(ErrorMessages.invalidEmail, e.getMessage()); 
        }
    }

    @Test
    public void testCreateClientDOT() { // Testing if an email with a .@ can be used to create an account
        try {
            Client client = service.createClient(PROFILE_DOB, EMAIL_DOT, PROFILE_PASSWORD, 
                                                PROFILE_PHONENUMBER, PROFILE_ADDRESS, CLIENT_FNAME, CLIENT_LNAME);
        } catch (Exception e) {
            assertEquals(ErrorMessages.invalidEmail, e.getMessage()); 
        }
    }

    @Test
    public void testCreateClientNA() { // Testing if an email with no @ can be used to create an account
        try {
            Client client = service.createClient(PROFILE_DOB, EMAIL_NO_AT, PROFILE_PASSWORD, 
                                                PROFILE_PHONENUMBER, PROFILE_ADDRESS, CLIENT_FNAME, CLIENT_LNAME);
        } catch (Exception e) {
            assertEquals(ErrorMessages.invalidEmail, e.getMessage()); 
        }
    }

    @Test
    public void testCreateClientSD() { // Testing if an email with a symbol in the domain name can be used to create an account
        try {
            Client client = service.createClient(PROFILE_DOB, EMAIL_SYMB_DN, PROFILE_PASSWORD, 
                                                PROFILE_PHONENUMBER, PROFILE_ADDRESS, CLIENT_FNAME, CLIENT_LNAME);
        } catch (Exception e) {
            assertEquals(ErrorMessages.invalidEmail, e.getMessage()); 
        }
    }

    @Test
    public void testCreateClientNoDot() { // Testing if an email with no . after @ can be used to create an account
        try {
            Client client = service.createClient(PROFILE_DOB, EMAIL_NO_DOT, PROFILE_PASSWORD, 
                                                PROFILE_PHONENUMBER, PROFILE_ADDRESS, CLIENT_FNAME, CLIENT_LNAME);
        } catch (Exception e) {
            assertEquals(ErrorMessages.invalidEmail, e.getMessage()); 
        }
    }

    @Test
    public void testCreateClient1TLD() { // Testing if an email with a one letter top-level domain can be used to create an account.
        try {
            Client client = service.createClient(PROFILE_DOB, EMAIL_1LETTER_DOMAIN, PROFILE_PASSWORD, 
                                                PROFILE_PHONENUMBER, PROFILE_ADDRESS, CLIENT_FNAME, CLIENT_LNAME);
        } catch (Exception e) {
            assertEquals(ErrorMessages.invalidEmail, e.getMessage()); 
        }
    }

    @Test
    public void testCreateClient8TLD() { // Testing if an email with an eight letter top-level domain can be used to create an account.
        try {
            Client client = service.createClient(PROFILE_DOB, EMAIL_8LETTER_DOMAIN, PROFILE_PASSWORD, 
                                                PROFILE_PHONENUMBER, PROFILE_ADDRESS, CLIENT_FNAME, CLIENT_LNAME);
        } catch (Exception e) {
            assertEquals(ErrorMessages.invalidEmail, e.getMessage()); 
        }
    }

    @Test
    public void testCreateClientNP() { // Testing if a null password can be used to create an account.
        try {
            Client client = service.createClient(PROFILE_DOB, UNREGISTERED_CLIENT_EMAIL, null, 
                                                PROFILE_PHONENUMBER, PROFILE_ADDRESS, CLIENT_FNAME, CLIENT_LNAME);
        } catch (Exception e) {
            assertEquals(ErrorMessages.invalidPassword, e.getMessage()); 
        }
    }

    @Test
    public void testCreateClient6CP() { // Testing if a less than six character password can be used to create an account.
        try {
            Client client = service.createClient(PROFILE_DOB, UNREGISTERED_CLIENT_EMAIL, FEW_CHAR_PASS, 
                                                PROFILE_PHONENUMBER, PROFILE_ADDRESS, CLIENT_FNAME, CLIENT_LNAME);
        } catch (Exception e) {
            assertEquals(ErrorMessages.invalidPassword, e.getMessage()); 
        }
    }

    @Test
    public void testCreateClientLP() { // Testing if a longer than 10 digit phone number can be used to create an account.
        try {
            Client client = service.createClient(PROFILE_DOB, UNREGISTERED_CLIENT_EMAIL, PROFILE_PASSWORD, 
                                                LONG_PHONENUMBER, PROFILE_ADDRESS, CLIENT_FNAME, CLIENT_LNAME);
        } catch (Exception e) {
            assertEquals(ErrorMessages.invalidPhoneNumber, e.getMessage()); 
        }
    }

    @Test
    public void testCreateClientNN() { // Testing if a phone number with non-numeric characters can be used to create an account.
        try {
            Client client = service.createClient(PROFILE_DOB, UNREGISTERED_CLIENT_EMAIL, PROFILE_PASSWORD, 
                                                NON_NUMERIC_PHONENUMBER, PROFILE_ADDRESS, CLIENT_FNAME, CLIENT_LNAME);
        } catch (Exception e) {
            assertEquals(ErrorMessages.invalidPhoneNumber, e.getMessage()); 
        }
    }

    @Test
    public void testCreateClientNullAddress() { // Testing if a null address can be used to create an account.
        try {
            Client client = service.createClient(PROFILE_DOB, UNREGISTERED_CLIENT_EMAIL, PROFILE_PASSWORD, 
                                                PROFILE_PHONENUMBER, null, CLIENT_FNAME, CLIENT_LNAME);
        } catch (Exception e) {
            assertEquals(ErrorMessages.invalidAddress, e.getMessage()); 
        }
    }

    @Test
    public void testCreateClientBA() { // Testing if a blank address can be used to create an account.
        try {
            Client client = service.createClient(PROFILE_DOB, UNREGISTERED_CLIENT_EMAIL, PROFILE_PASSWORD, 
                                                PROFILE_PHONENUMBER, "", CLIENT_FNAME, CLIENT_LNAME);
        } catch (Exception e) {
            assertEquals(ErrorMessages.invalidAddress, e.getMessage()); 
        }
    }

    @Test
    public void testCreateClientNF() { // Testing if a null first name can be used to create an account.
        try {
            Client client = service.createClient(PROFILE_DOB, UNREGISTERED_CLIENT_EMAIL, PROFILE_PASSWORD, 
                                                PROFILE_PHONENUMBER, PROFILE_ADDRESS, null, CLIENT_LNAME);
        } catch (Exception e) {
            assertEquals(ErrorMessages.invalidFirstName, e.getMessage()); 
        }
    }

    @Test
    public void testCreateClientBF() { // Testing if a blank first name can be used to create an account.
        try {
            Client client = service.createClient(PROFILE_DOB, UNREGISTERED_CLIENT_EMAIL, PROFILE_PASSWORD, 
                                                PROFILE_PHONENUMBER, PROFILE_ADDRESS, "", CLIENT_LNAME);
        } catch (Exception e) {
            assertEquals(ErrorMessages.invalidFirstName, e.getMessage()); 
        }
    }

    @Test
    public void testCreateClientNL() { // Testing if a null last name can be used to create an account.
        try {
            Client client = service.createClient(PROFILE_DOB, UNREGISTERED_CLIENT_EMAIL, PROFILE_PASSWORD, 
                                                PROFILE_PHONENUMBER, PROFILE_ADDRESS, CLIENT_FNAME, null);
        } catch (Exception e) {
            assertEquals(ErrorMessages.invalidLastName, e.getMessage()); 
        }
    }

    @Test
    public void testCreateClientBL() { // Testing if a blank last name can be used to create an account.
        try {
            Client client = service.createClient(PROFILE_DOB, UNREGISTERED_CLIENT_EMAIL, PROFILE_PASSWORD, 
                                                PROFILE_PHONENUMBER, PROFILE_ADDRESS, CLIENT_FNAME, "");
        } catch (Exception e) {
            assertEquals(ErrorMessages.invalidLastName, e.getMessage()); 
        }
    }

}