package ca.mcgill.ecse321.petshelter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
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
	
	private static final Integer POSTING_ID2 = (Integer)2;
	private static final Date POSTING_DATE2 = Date.valueOf("1999-02-01");
	private static final String POSTING_PICTURE2 = "Labrador Image";
	private static final String POSTING_DESCRIPTION2 = "very nice friendly dog that loves to be petted";
	private static final String POSTING_PETNAME2 = "Tino";
	private static final String POSTING_PETBREED2 = "Labrador";
	private static final Date POSTING_PET_DOB2 = Date.valueOf("2008-02-01");
	
	// Dummy client attributes
	private static final Date CLIENT_DOB = Date.valueOf("1998-01-01");
    private static final String CLIENT_EMAIL_LOGGEDIN = "muffin_man@gmail.com"; // Testing logged in account
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
		lenient().when(service.toList(postingDAO.findAll())).thenAnswer((InvocationOnMock invocation) -> {
	           ArrayList<Posting> allPostings = new ArrayList<Posting>(); 
	           
	           Posting posting1 = new Posting();
	           Profile profile = new Client();
	           profile.setAddress(CLIENT_ADDRESS);
	           profile.setDateOfBirth(CLIENT_DOB);
	           profile.setEmail(CLIENT_EMAIL_LOGGEDIN);
	           profile.setPhoneNumber(CLIENT_PHONENUMBER);
	           posting1.setId(POSTING_ID1);
	           posting1.setDate(POSTING_DATE);
	           posting1.setDescription(POSTING_DESCRIPTION);
	           posting1.setPicture(POSTING_PICTURE);
	           posting1.setPetBreed(POSTING_PETBREED);
	           posting1.setPetName(POSTING_PETNAME);
	           posting1.setPetDateOfBirth(POSTING_PET_DOB);
	           posting1.setProfile(profile);
	           Application application1 = new Application();
	           Set <Application> applications1 = new HashSet <Application>();
	           application1.setStatus(APPLICATION_STATUS_ACCEPTED);
	           application1.setPosting(posting1);
	           applications1.add(application1);
	           Application application3 = new Application();
	           application3.setStatus(APPLICATION_STATUS_REJECTED);
	           application3.setPosting(posting1);
	           applications1.add(application3);
	           posting1.setApplication(applications1);
			
	           Posting posting2 = new Posting();
	           Profile profile2 = new Admin();
	           profile2.setAddress(ADMIN_ADDRESS);
	           profile2.setDateOfBirth(ADMIN_DOB);
	           profile2.setEmail(ADMIN_EMAIL);
	           profile2.setPhoneNumber(ADMIN_PHONENUMBER);
	           posting2.setId(POSTING_ID2);
	           posting2.setDate(POSTING_DATE2);
	           posting2.setDescription(POSTING_DESCRIPTION2);
	           posting2.setPicture(POSTING_PICTURE2);
	           posting2.setPetBreed(POSTING_PETBREED2);
	           posting2.setPetName(POSTING_PETNAME2);
	           posting2.setPetDateOfBirth(POSTING_PET_DOB2);
	           posting2.setProfile(profile2);
	           Set <Application> applications2 = new HashSet <Application>();
	           Application application2 = new Application();
	           application2.setStatus(APPLICATION_STATUS_PENDING);
	           application2.setPosting(posting2);
	           applications2.add(application2);
	           posting2.setApplication(applications2);
	           
	           allPostings.add(posting1);
	           allPostings.add(posting2);
	           
	           return allPostings;
		});
		
		
		// Whenever the posting is saved, just return the parameter object
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
	    };
		        
		lenient().when(postingDAO.save(any(Posting.class))).thenAnswer(returnParameterAsAnswer);
		doNothing().when(postingDAO).delete(any(Posting.class));
	}
	
	// Test for if the profile is null 
	@Test
	public void testCreatePostingProfileNull() {
		String error = null;
		Posting posting = null;
		try {
			posting = service.createPosting(null, POSTING_DATE, POSTING_PETNAME, POSTING_PET_DOB, POSTING_PETBREED, POSTING_PICTURE, POSTING_DESCRIPTION);
		}
		
		catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(posting);
		assertEquals(ErrorMessages.invalidProfile, error);
		
	}
	
	@Test
	public void testCreatePostingDateNull() {
		String error = null;
		Posting posting = null;
		Client account = new Client();
		
		try {
			posting = service.createPosting(account, null, POSTING_PETNAME, POSTING_PET_DOB, POSTING_PETBREED,POSTING_PICTURE, POSTING_DESCRIPTION);
		}
		catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(posting);
		assertEquals(ErrorMessages.invalidDate,error);
	}
	
	@Test
	public void testCreatePostingPetNameNull() {
		String error = null;
		Posting posting = null;
		Client account = new Client();
		
		try {
			posting = service.createPosting(account, POSTING_DATE, null, POSTING_PET_DOB, POSTING_PETBREED,POSTING_PICTURE, POSTING_DESCRIPTION);
		}
		catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(posting);
		assertEquals(ErrorMessages.invalidPetName,error);
	}
	
	
	
	// open postings test
	//check that only postings that don't have an accepted application are returned
	@Test
	public void testGetOpenPostings() {
		
		List <Posting> openPostings = service.getOpenPostings();		
		for(Posting posting: openPostings) {
			for(Application application: posting.getApplication()) {
				assertNotEquals(ApplicationStatus.accepted, application.getStatus());
			}
		}	
	}
	
}
