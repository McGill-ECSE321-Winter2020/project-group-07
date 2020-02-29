package ca.mcgill.ecse321.petshelter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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
import ca.mcgill.ecse321.petshelter.model.*;
import ca.mcgill.ecse321.petshelter.ErrorMessages;


@ExtendWith(MockitoExtension.class)
public class PostingServiceTests {

	
	@Mock
	private PostingRepository postingDAO;
	
	@InjectMocks
	private PetShelterService service;
	
	// Dummy Posting attributes
	private static final Integer POSTING_ID1 = (Integer)1;
	private static final Date POSTING_DATE = Date.valueOf("1999-01-01");
	private static final String POSTING_PICTURE = "Rotweiler Image";
	private static final String POSTING_DESCRIPTION = "very nice friendly dog that loves to play";
	private static final String POSTING_PETNAME = "Rex";
	private static final String POSTING_PETBREED = "Rotweiler";
	private static final Date POSTING_PET_DOB = Date.valueOf("2006-01-01");
	private static final List <Comment> POSTING_COMMENTS = new ArrayList <Comment>(); 
	private static final List <Application> POSTING_APPLICATIONS = new ArrayList <Application>();
	
	private static final Integer POSTING_ID2 = (Integer)2;
	private static final Date POSTING_DATE2 = Date.valueOf("1999-02-01");
	private static final String POSTING_PICTURE2 = "Labrador Image";
	private static final String POSTING_DESCRIPTION2 = "very nice friendly dog that loves to be petted";
	private static final String POSTING_PETNAME2 = "Tino";
	private static final String POSTING_PETBREED2 = "Labrador";
	private static final Date POSTING_PET_DOB2 = Date.valueOf("2008-02-01");
	private static final List <Comment> POSTING_COMMENTS2 = new ArrayList <Comment>(); 
	private static final List <Application> POSTING_APPLICATIONS2 = new ArrayList <Application>();
	
	
	// Dummy comment attributes
	private static final Date COMMENT_DATE = Date.valueOf("1999-10-31");
	private static final Posting COMMENT_POSTING = new Posting();
	private static final Client COMMENT_CLIENT = new Client();
	private static final Admin COMMENT_ADMIN = new Admin();
	private static final String COMMENT_CONTENT = "Wow, your poodle looks so nice";
	
	// Dummy client attributes
	private static final Date CLIENT_DOB = Date.valueOf("1998-01-01");
    private static final String CLIENT_EMAIL_LOGGEDIN = "muffin_man@gmail.com"; // Testing logged in account
    private static final String CLIENT_EMAIL_LOGGEDOUT = "muffin_woman@gmail.com"; // Testing logged out account
    private static final String CLIENT_PHONENUMBER = "5555555555";
    private static final String CLIENT_ADDRESS = "1729 Drury Lane";
	
	//Dummy admin attributes
	private static final Date ADMIN_DOB = Date.valueOf("1998-01-01");
	private static final String ADMIN_EMAIL = "pet_shelter@petshelter.com";
    private static final String ADMIN_PHONENUMBER = "5555555555";
    private static final String ADMIN_ADDRESS = "123 Animal Crossing";
	
	//Dummy application attributes
    private static final ApplicationStatus APPLICATION_STATUS_ACCEPTED = ApplicationStatus.accepted;
    private static final ApplicationStatus APPLICATION_STATUS_REJECTED = ApplicationStatus.rejected;
    private static final ApplicationStatus APPLICATION_STATUS_PENDING = ApplicationStatus.pending;
    
    
	//Dummy illegitimate attributes for a posting
	
	
	// Test stubs
	@BeforeEach
	public void setMockOutput() {
		// When finding a posting
		lenient().when(postingDAO.findPostingById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(1)) {
				
				
				
				return null;
			}else {
				return null;
			}
		});
		
		
		// Whenever the posting is saved, just return the parameter object
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
	    };
		        
		lenient().when(postingDAO.save(any(Posting.class))).thenAnswer(returnParameterAsAnswer);
	}
	
	
	
	// Create Posting tests
	
	
	// Update Posting tests
	

	// Delete Posting tests
	
	
	// Get Open Posting tests
	
	// open postings test
	@Test
	public void testGetOpenPostings() {
		
	}
	
	//No open postings test
	@Test
	public void testNoOpenPostings() {
		
	}
	
	//No postings test
	@Test
	public void testNoPostings() {
		
	}
	
}
