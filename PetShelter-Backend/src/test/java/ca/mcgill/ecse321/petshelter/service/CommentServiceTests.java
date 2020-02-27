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
	private static final Date PROFILE_DOB = Date.valueOf("1991-01-01");
	private static final Date UNDERAGE_PROFILE_DOB = Date.valueOf("1992-01-01");
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
	private static final Date POSTING_DATE = Date.valueOf("1991-01-01");
	private static final Date UNDERAGE_POSTING_DATE = Date.valueOf("1992-01-01");
	private static final String POSTING_PICTURE = "Rotweiler Image";
	private static final String POSTING_DESCRIPTION = "very nice friendly dog that loves to play";
	private static final String POSTING_PETNAME = "Rex";
	private static final String POSTING_PETBREED = "Rotweiler";
	private static final Date POSTING_PETDOB = Date.valueOf("2006-01-01");
	private static final List <Comment> POSTING_COMMENTS = new ArrayList <Comment>(); 
	private static final List <Application> POSTING_APPLICATIONS = new ArrayList <Application>();
	
	//Dummy illegitimate attributes for creating a new comment
	private static final Integer BAD_COMMENT_ID1= (Integer)2;
	private static final Date UNDERAGE_COMMENT_DATE = Date.valueOf("1991-10-31");
	private static final String NULL_COMMENT_CONTENT = "";
	
	private static final Integer BAD_COMMENT_ID2= (Integer)3;
	private static final Date NULL_COMMENT_DATE = null;
	
	
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
	            client.setIsLoggedIn(PROFILE_LOGGEDIN);
	            comment.setPosting(posting);
	            posting.setDate(POSTING_DATE);
//	            client.setPassword(PROFILE_PASSWORD);
//	            client.setPhoneNumber(PROFILE_PHONENUMBER);
//	            client.setAddress(PROFILE_ADDRESS);
//	            client.setIsLoggedIn(PROFILE_LOGGEDIN);
//	            client.setFirstName(CLIENT_FNAME);
//	            client.setLastName(CLIENT_LNAME);
	            return comment;
	        } else  if(invocation.getArgument(0).equals(2)){
	        	Comment comment = new Comment(); // Dummy comment
	    		Client client = new Client();
	    		Posting posting = new Posting();
	    		comment.setId(BAD_COMMENT_ID1);
	    		comment.setContent(NULL_COMMENT_CONTENT);
	    		comment.setDate(UNDERAGE_COMMENT_DATE);
	    		comment.setProfile(client);
	    		client.setDateOfBirth(PROFILE_DOB);
	            client.setEmail(PROFILE_EMAIL_LOGGEDIN); 
	            client.setIsLoggedIn(PROFILE_LOGGEDIN);
	            comment.setPosting(posting);
	            posting.setDate(POSTING_DATE);
	            return comment;
	        } else  if(invocation.getArgument(0).equals(3)) {
	        	Comment comment = new Comment(); // Dummy comment
	    		Client client = new Client();
	    		client.setIsLoggedIn(PROFILE_LOGGEDIN);
	    		Posting posting = new Posting();
	    		comment.setId(BAD_COMMENT_ID2);
	    		comment.setContent(NULL_COMMENT_CONTENT);
	    		comment.setDate(NULL_COMMENT_DATE);
	            return comment;
	        }else {
	        	return null;
	        }
	    });
		
		// Whenever the profile is saved, just return the parameter object
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
	    };
		        
		lenient().when(commentDAO.save(any(Comment.class))).thenAnswer(returnParameterAsAnswer);
		
	}
	
	//commentOnPosting tests
	
	//testing valid comment
	@Test
	public void testValidComment(){
		
		String content = COMMENT_CONTENT;
		Date date = COMMENT_DATE;
		Client profile = new Client();
		profile.setEmail(PROFILE_EMAIL_LOGGEDIN);
		profile.setIsLoggedIn(PROFILE_LOGGEDIN);
		profile.setDateOfBirth(PROFILE_DOB);
		Posting posting = new Posting();
		posting.setDate(POSTING_DATE);
		posting.setId(1);
		
		try {
			Comment comment = service.commentOnPosting(profile, posting, content, date);
		} catch (IllegalArgumentException e) {
			fail();
		}
	}
	
	//Testing comment with null content
	@Test
	public void testNullContentComment(){
		
		String content = NULL_COMMENT_CONTENT;
		Date date = COMMENT_DATE;
		Client profile = new Client();
		profile.setIsLoggedIn(PROFILE_LOGGEDIN);
		profile.setDateOfBirth(PROFILE_DOB);
		Posting posting = new Posting();
		posting.setDate(POSTING_DATE);
		
		try {
			Comment comment = service.commentOnPosting(profile, posting, content, date);
		} catch (IllegalArgumentException e) {
			assertEquals(ErrorMessages.invalidContentComment, e.getMessage());
		}
	}
	
	//Testing comment with a date before date of creation of the posting
	@Test
	public void testDateBeforePostingDateComment(){
		
		String content = COMMENT_CONTENT;
		Date date = UNDERAGE_COMMENT_DATE;
		Client profile = new Client();
		profile.setIsLoggedIn(PROFILE_LOGGEDIN);
		profile.setDateOfBirth(PROFILE_DOB);
		Posting posting = new Posting();
		posting.setDate(UNDERAGE_POSTING_DATE);
		
		try {
			Comment comment = service.commentOnPosting(profile, posting, content, date);
		} catch (IllegalArgumentException e) {
			assertEquals(ErrorMessages.invalidDateCommentPosting, e.getMessage());
		}
	}
	
	//Testing comment with a date before date of creation of the profile
	@Test
	public void testDateBeforeProfileDateComment(){
			
		String content = COMMENT_CONTENT;
		Date date = UNDERAGE_COMMENT_DATE;
		Client profile = new Client();
		profile.setIsLoggedIn(PROFILE_LOGGEDIN);
		profile.setDateOfBirth(UNDERAGE_PROFILE_DOB);
		Posting posting = new Posting();
		posting.setDate(POSTING_DATE);
			
		try {
			Comment comment = service.commentOnPosting(profile, posting, content, date);
		} catch (IllegalArgumentException e) {
			assertEquals(ErrorMessages.invalidDateCommentProfile, e.getMessage());
		}
	}
	//Testing comment with a logged out profile
	@Test
	public void testProfileNotLoggedInComment(){
			
		String content = COMMENT_CONTENT;
		Date date = COMMENT_DATE;
		Client profile = new Client();
		profile.setIsLoggedIn(PROFILE_LOGGEDOUT);
		profile.setDateOfBirth(PROFILE_DOB);
		Posting posting = new Posting();
		posting.setDate(POSTING_DATE);
			
		try {
			Comment comment = service.commentOnPosting(profile, posting, content, date);
		} catch (IllegalArgumentException e) {
			assertEquals(ErrorMessages.invalidProfileNotLoggedIn, e.getMessage());
		}
	}
	//Testing comment with a null profile
	@Test
	public void testNullProfileComment(){
			
		String content = COMMENT_CONTENT;
		Date date = COMMENT_DATE;
		Client profile = null;
		Posting posting = new Posting();
		posting.setDate(POSTING_DATE);
			
		try {
			Comment comment = service.commentOnPosting(profile, posting, content, date);
		} catch (IllegalArgumentException e) {
			assertEquals(ErrorMessages.invalidProfile, e.getMessage());
		}
	}
	
	//Testing comment with a null posting
	@Test
	public void testNullPostingComment(){
			
		String content = COMMENT_CONTENT;
		Date date = COMMENT_DATE;
		Client profile = new Client();
		profile.setIsLoggedIn(PROFILE_LOGGEDOUT);
		profile.setDateOfBirth(PROFILE_DOB);
		Posting posting = null;
			
		try {
			Comment comment = service.commentOnPosting(profile, posting, content, date);
		} catch (IllegalArgumentException e) {
			assertEquals(ErrorMessages.invalidPosting, e.getMessage());
		}
	}
	
	//GetComments tests
}
