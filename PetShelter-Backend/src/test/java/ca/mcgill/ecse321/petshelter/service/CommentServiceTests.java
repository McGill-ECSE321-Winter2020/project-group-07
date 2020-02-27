package ca.mcgill.ecse321.petshelter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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
	private static final Integer COMMENT_ID = (Integer)1;
	private static final Date COMMENT_DATE = Date.valueOf("1999-10-31");
	private static final String COMMENT_CONTENT = "Wow, your poodle looks so nice";
	
	//Dummy profile attributes
	private static final Date PROFILE_DOB = Date.valueOf("1992-01-01");
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
	private static final String POSTING_PICTURE = "Rotweiler Image";
	private static final String POSTING_DESCRIPTION = "very nice friendly dog that loves to play";
	private static final String POSTING_PETNAME = "Rex";
	private static final String POSTING_PETBREED = "Rotweiler";
	private static final Date POSTING_PETDOB = Date.valueOf("2006-01-01");
	private static final List <Comment> POSTING_COMMENTS = new ArrayList <Comment>(); 
	private static final List <Application> POSTING_APPLICATIONS = new ArrayList <Application>();
	
	//Dummy illegitimate attributes for creating a new comment
	private static final Integer BAD_COMMENT_ID = (Integer)1;
	private static final Date UNDERAGE_COMMENT_DATE = Date.valueOf("1991-10-31");
	private static final String NULL_COMMENT_CONTENT = "";
	
	// Test stubs
	@BeforeEach
	public void setMockOutput() {
		// When finding a comment
		lenient().when(commentDAO.findCommentById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
	        if(invocation.getArgument(0).equals(1)) {
	    		Comment comment = new Comment(); // Dummy comment
	    		Client client = new Client();
	    		Posting posting = new Posting();
	    		comment.setId(COMMENT_ID);
	    		comment.setContent(COMMENT_CONTENT);
	    		comment.setDate(COMMENT_DATE);
	    		comment.setProfile(client);
	    		client.setDateOfBirth(PROFILE_DOB);
	            client.setEmail(PROFILE_EMAIL_LOGGEDIN); 
	            comment.setPosting(posting);
	            posting.setDate(POSTING_DATE);
//	            client.setPassword(PROFILE_PASSWORD);
//	            client.setPhoneNumber(PROFILE_PHONENUMBER);
//	            client.setAddress(PROFILE_ADDRESS);
//	            client.setIsLoggedIn(PROFILE_LOGGEDIN);
//	            client.setFirstName(CLIENT_FNAME);
//	            client.setLastName(CLIENT_LNAME);
	            return comment;
	        } else {
	            return null;
	        }
	    });
		

		
	}
	
	
	//Testing comment with null content
	
	
	//Testing comment with a date before date of creation of the posting
	
	
	//Testing comment with a profile that is not logged in
	
	
	//Testing comment with an invalid profile
	
	
}
