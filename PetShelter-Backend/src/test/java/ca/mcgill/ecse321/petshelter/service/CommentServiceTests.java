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
import java.util.List;
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
import ca.mcgill.ecse321.petshelter.dto.ApplicationDTO;
import ca.mcgill.ecse321.petshelter.dto.CommentDTO;
import ca.mcgill.ecse321.petshelter.dto.ProfileDTO;
import ca.mcgill.ecse321.petshelter.model.*;
import ca.mcgill.ecse321.petshelter.ErrorMessages;


@ExtendWith(MockitoExtension.class)
public class CommentServiceTests {

	@Mock
	private CommentRepository commentDAO;
	
	@InjectMocks
	private PetShelterService service;
	
	// Dummy comment attributes
	private static final Date COMMENT_DATE = Date.valueOf("1999-10-31");
	private static final Posting COMMENT_POSTING = new Posting();
	private static final Client COMMENT_CLIENT = new Client();
	private static final Admin COMMENT_ADMIN = new Admin();
	private static final String COMMENT_CONTENT = "Wow, your poodle looks so nice";
	
	//Dummy profile attributes
	private static final Date PROFILE_DOB = Date.valueOf("1990-01-01");
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
	
	//Dummy admin attributes
	//same as profile
	
	//Dummy posting attributes
	private static final Date POSTING_DATE = Date.valueOf("1992-01-01");
	private static final String picture = "Rotweiler Image";
	private static final String description = "very nice friendly dog that loves to play";
	private static final String petName = "Rex";
	private static final String petBreed = "Rotweiler";
	private static final Date petDateOfBirth = Date.valueOf("2006-01-01");
	private static final List <Comment> comments = new ArrayList <Comment>(); 
	private static final List <Application> applications = new ArrayList <Application>();
	
	//Dummy illegitimate attributes for creating a new comment
	
	// Test stubs
	@BeforeEach
	public void setMockOutput() {
		
	}
	
	
	//Testing comment with null content
	
	
	//Testing comment with a date before date of creation of the posting
	
	
	//Testing comment with a profile that is not logged in
	
	
	//Testing comment with an invalid profile
	
	
}
