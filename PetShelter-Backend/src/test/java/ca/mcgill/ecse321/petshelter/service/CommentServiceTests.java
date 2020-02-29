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
	
	private static final Integer COMMENT_ID2 = (Integer)4;
	private static final Date COMMENT_DATE2 = Date.valueOf("1999-11-01");
	private static final String COMMENT_CONTENT2 = "Why thank you Bethany!";
	
	
	
	//Dummy profile attributes
	private static final Date PROFILE_DOB = Date.valueOf("1991-01-01");
	private static final Date UNDERAGE_PROFILE_DOB = Date.valueOf("1992-01-01");
    private static final String PROFILE_EMAIL_LOGGEDIN = "muffin_man@gmail.com"; // Testing logged in account
    private static final String PROFILE_EMAIL_LOGGEDOUT = "muffin_woman@gmail.com"; // Testing logged out account
    private static final boolean PROFILE_LOGGEDIN = true; // Testing logged in account
    private static final boolean PROFILE_LOGGEDOUT = false; // Testing logged out account
    
	
	//Dummy posting attributes
	private static final Date POSTING_DATE = Date.valueOf("1991-01-01");
	private static final Date UNDERAGE_POSTING_DATE = Date.valueOf("1992-01-01");
	private static final String POSTING_PICTURE = "Rotweiler Image";
	private static final String POSTING_DESCRIPTION = "very nice friendly dog that loves to play";
	private static final String POSTING_PETNAME = "Rex";
	private static final String POSTING_PETBREED = "Rotweiler";
	private static final Date POSTING_PETDOB = Date.valueOf("2006-01-01");
	
	//Dummy illegitimate attributes for creating a new comment
	private static final Integer BAD_COMMENT_ID1= (Integer)2;
	private static final Date UNDERAGE_COMMENT_DATE = Date.valueOf("1991-10-31");
	private static final String NULL_COMMENT_CONTENT = "";
	
	private static final Integer BAD_COMMENT_ID2 = (Integer)3;
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
	    		comment.setPosting(posting);
	            return comment;
	        }else {
	        	return null;
	        }
	    });
		
		// Whenever the comment is saved, just return the parameter object
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
		profile.setEmail(PROFILE_EMAIL_LOGGEDOUT);
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
	
	//Testing with null Posting
	@Test
	public void testNullPostingGetComments() {
		
		Posting posting = null;
		try {
			List<Comment> comments = service.getComments(posting);
		} catch (Exception e) {
			assertEquals(ErrorMessages.invalidPosting, e.getMessage());
		}
	}
	
	
	//Testing with a posting with valid comments
	@Test
	public void testPostingWithValidCommentsGetComments() {
		
		Posting posting = new Posting();
		Profile profile = new Client();
		posting.setDate(UNDERAGE_POSTING_DATE);
		posting.setDescription(POSTING_DESCRIPTION);
		posting.setPicture(POSTING_PICTURE);
		posting.setPetBreed(POSTING_PETBREED);
		posting.setPetName(POSTING_PETNAME);
		posting.setPetDateOfBirth(POSTING_PETDOB);
		posting.setProfile(profile);
		
		List <Comment> commentList = new ArrayList<Comment>();
		
		Comment comment1 = new Comment();
		comment1.setContent(COMMENT_CONTENT);
		comment1.setDate(COMMENT_DATE);
		comment1.setPosting(posting);
		comment1.setProfile(profile);
		comment1.setId(COMMENT_ID);
		commentList.add(comment1);
		
		Comment comment2 = new Comment();
		comment2.setContent(COMMENT_CONTENT2);
		comment2.setDate(COMMENT_DATE2);
		comment2.setPosting(posting);
		comment2.setProfile(profile);
		comment2.setId(COMMENT_ID2);
		commentList.add(comment2);
		
		try {
			List<Comment> comments = service.getComments(posting);
		} catch (Exception e) {
			fail();
		}
	}
	
	
	//Testing with a posting with invalid comments
	@Test
	public void testPostingWithInvalidCommentsGetComments() {
			
		Posting posting = new Posting();
		Profile profile = new Client();
		posting.setDate(UNDERAGE_POSTING_DATE);
		posting.setDescription(POSTING_DESCRIPTION);
		posting.setPicture(POSTING_PICTURE);
		posting.setPetBreed(POSTING_PETBREED);
		posting.setProfile(profile);
			
		List <Comment> commentList = new ArrayList<Comment>();
			
		Comment comment1 = new Comment();
		comment1.setContent(COMMENT_CONTENT);
		comment1.setDate(UNDERAGE_COMMENT_DATE);
		comment1.setPosting(posting);
		comment1.setProfile(profile);
		comment1.setId(COMMENT_ID);
		commentList.add(comment1);
		
		Comment comment2 = new Comment();
		comment2.setContent(COMMENT_CONTENT2);
		comment2.setDate(COMMENT_DATE2);
		comment2.setPosting(posting);
		comment2.setProfile(profile);
		comment2.setId(COMMENT_ID2);
		commentList.add(comment2);
			
		try {
			List<Comment> comments = service.getComments(posting);
		} catch (Exception e) {
			assertEquals(ErrorMessages.invalidDateCommentPosting, e.getMessage());
		}
	}
	
	
	//testing with a posting without comments
	@Test
	public void testPostingWithoutCommentsGetComments() {
		
		Posting posting = new Posting();
		Profile profile = new Client();
		posting.setDate(POSTING_DATE);
		posting.setDescription(POSTING_DESCRIPTION);
		posting.setPicture(POSTING_PICTURE);
		posting.setPetBreed(POSTING_PETBREED);
		posting.setProfile(profile);
			
		List <Comment> commentList = new ArrayList<Comment>();
			
		try {
			List<Comment> comments = service.getComments(posting);
			assertEquals(comments.isEmpty(), true);
		} catch (Exception e) {
			fail();
		}
	}
}
