package ca.mcgill.ecse321.petshelter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;

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

import ca.mcgill.ecse321.petshelter.ErrorMessages;
import ca.mcgill.ecse321.petshelter.dao.PostingRepository;
import ca.mcgill.ecse321.petshelter.dao.ProfileRepository;
import ca.mcgill.ecse321.petshelter.model.Admin;
import ca.mcgill.ecse321.petshelter.model.Application;
import ca.mcgill.ecse321.petshelter.model.ApplicationStatus;
import ca.mcgill.ecse321.petshelter.model.Client;
import ca.mcgill.ecse321.petshelter.model.Posting;
import ca.mcgill.ecse321.petshelter.model.Profile;

@ExtendWith(MockitoExtension.class)
public class PostingServiceTests {

	@Mock
	private PostingRepository postingDAO;

	@Mock
	private ProfileRepository profileDAO;

	
	@InjectMocks
	private PetShelterService service;

	// Dummy Posting attributes
	private static final Integer POSTING_ID1 = (Integer) 1;
	private static final Date POSTING_DATE = Date.valueOf("1999-01-01");
	private static final String POSTING_PICTURE = "Rotweiler Image";
	private static final String POSTING_DESCRIPTION = "very nice friendly dog that loves to play";
	private static final String POSTING_PETNAME = "Rex";
	private static final String POSTING_PETBREED = "Rotweiler";
	private static final Date POSTING_PET_DOB = Date.valueOf("2006-01-01");

	private static final Integer POSTING_ID2 = (Integer) 2;
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

	// Dummy admin attributes
	private static final Date ADMIN_DOB = Date.valueOf("1998-01-01");
	private static final String ADMIN_EMAIL = "pet_shelter@petshelter.com";
	private static final String ADMIN_PHONENUMBER = "5555555555";
	private static final String ADMIN_ADDRESS = "123 Animal Crossing";

	// Dummy application attributes
	private static final ApplicationStatus APPLICATION_STATUS_ACCEPTED = ApplicationStatus.accepted;
	private static final ApplicationStatus APPLICATION_STATUS_REJECTED = ApplicationStatus.rejected;
	private static final ApplicationStatus APPLICATION_STATUS_PENDING = ApplicationStatus.pending;

	// Invalid attributes
	private static final long timeFourMonthsFromNow = 1592659829000L;
	private static final String invalidPetName = "c3po";
	private static final String invalidBreed = "labrador and husky";
	private static final String tooLongDescription = "Are you looking for a friend? Well so am I! "
			+ "Someone to walk on the beach with? Me too! "
			+ "How about a cuddle on the couch and someone who will listen and not judge you? "
			+ "So am I! We have so much in common I'm sure we could even be BFF! "
			+ "I'm not looking for just anyone. Someone who will appreciate my protective nature (no door bells needed!)! "
			+ "I'm not only a little cutie with my curled tail and big brown eyes, I have substance and intelligence! "
			+ "I have learned the basics like sit, down and would love to continue learning new things. "
			+ "My foster mom says I would be a really good agility, flyball or jogging partner! "
			+ "I've got these long bouncy legs and boy can I jump! "
			+ "I've gotten a good start in my foster home and already have mastered crate training. "
			+ "So you have to wonder why I'm a Heartstrings dog since I'm smart, cute, healthy, house trained and a loyal companion. "
			+ "I've got one teensy tiny flaw. I'm not fond of strangers whether they're people (big and small) or dogs on walks. "
			+ "Actually I'm more scared than anything else so I'm not like those other silly types of dogs that slobber all over you at the first minute they meet you.";
			
	// Test stubs
	@BeforeEach
	public void setMockOutput() {
		// When finding a posting
		lenient().when(postingDAO.findPostingById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(CLIENT_EMAIL_LOGGEDIN.hashCode() * POSTING_DATE.hashCode())) {
				Posting posting = new Posting();
				posting.setId(CLIENT_EMAIL_LOGGEDIN.hashCode() * POSTING_DATE.hashCode());
				return posting;
			} else {
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
			Set<Application> applications1 = new HashSet<Application>();
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
			Set<Application> applications2 = new HashSet<Application>();
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
		lenient().doNothing().when(postingDAO).delete(any(Posting.class));
		lenient().when(profileDAO.save(any(Profile.class))).thenAnswer(returnParameterAsAnswer);
	}

	// Test for if the profile is null
	@Test
	public void testCreatePostingProfileNull() {
		String error = null;
		Posting posting = null;
		try {
			posting = service.createPosting(null, POSTING_DATE, POSTING_PETNAME, POSTING_PET_DOB, POSTING_PETBREED,
					POSTING_PICTURE, POSTING_DESCRIPTION);
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
			posting = service.createPosting(account, null, POSTING_PETNAME, POSTING_PET_DOB, POSTING_PETBREED,
					POSTING_PICTURE, POSTING_DESCRIPTION);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(posting);
		assertEquals(ErrorMessages.invalidDate, error);
	}

	@Test
	public void testCreatePostingPetNameNull() {
		String error = null;
		Posting posting = null;
		Client account = new Client();

		try {
			posting = service.createPosting(account, POSTING_DATE, null, POSTING_PET_DOB, POSTING_PETBREED,
					POSTING_PICTURE, POSTING_DESCRIPTION);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(posting);
		assertEquals(ErrorMessages.invalidPetName, error);
	}

	@Test
	public void testCreatePostingPetNameEmpty() {
		String error = null;
		Posting posting = null;
		Client account = new Client();

		try {
			posting = service.createPosting(account, POSTING_DATE, "", POSTING_PET_DOB, POSTING_PETBREED,
					POSTING_PICTURE, POSTING_DESCRIPTION);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(posting);
		assertEquals(ErrorMessages.invalidPetName, error);
	}

	@Test
	public void testCreatePostingPetNameWrong() {
		String error = null;
		Posting posting = null;
		Client account = new Client();

		try {
			posting = service.createPosting(account, POSTING_DATE, invalidPetName, POSTING_PET_DOB, POSTING_PETBREED,
					POSTING_PICTURE, POSTING_DESCRIPTION);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(posting);
		assertEquals(ErrorMessages.invalidPetName, error);
	}

	@Test
	public void testCreatePostingPetDOBNull() {
		String error = null;
		Posting posting = null;
		Client account = new Client();

		try {
			posting = service.createPosting(account, POSTING_DATE, POSTING_PETNAME, null, POSTING_PETBREED,
					POSTING_PICTURE, POSTING_DESCRIPTION);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(posting);
		assertEquals(ErrorMessages.invalidPetDOB, error);
	}

	@Test
	public void testCreatePostingPetDOBLater() {
		String error = null;
		Posting posting = null;
		Client account = new Client();
		Date later = new Date(timeFourMonthsFromNow);
		try {
			posting = service.createPosting(account, POSTING_DATE, POSTING_PETNAME, later, POSTING_PETBREED,
					POSTING_PICTURE, POSTING_DESCRIPTION);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(posting);
		assertEquals(ErrorMessages.invalidPetDOB, error);
	}

	@Test
	public void testCreatePostingPetBreedNull() {
		String error = null;
		Posting posting = null;
		Client account = new Client();
		try {
			posting = service.createPosting(account, POSTING_DATE, POSTING_PETNAME, POSTING_PET_DOB, null,
					POSTING_PICTURE, POSTING_DESCRIPTION);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(posting);
		assertEquals(ErrorMessages.invalidBreed, error);
	}

	@Test
	public void testCreatePostingPetBreedEmpty() {
		String error = null;
		Posting posting = null;
		Client account = new Client();
		try {
			posting = service.createPosting(account, POSTING_DATE, POSTING_PETNAME, POSTING_PET_DOB, "",
					POSTING_PICTURE, POSTING_DESCRIPTION);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(posting);
		assertEquals(ErrorMessages.invalidBreed, error);
	}

	@Test
	public void testCreatePostingPetBreedInvalid() {
		String error = null;
		Posting posting = null;
		Client account = new Client();
		try {
			posting = service.createPosting(account, POSTING_DATE, POSTING_PETNAME, POSTING_PET_DOB, invalidBreed,
					POSTING_PICTURE, POSTING_DESCRIPTION);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(posting);
		assertEquals(ErrorMessages.invalidBreed, error);
	}

	@Test
	public void testCreatePostingPictureNull() {
		String error = null;
		Posting posting = null;
		Client account = new Client();
		try {
			posting = service.createPosting(account, POSTING_DATE, POSTING_PETNAME, POSTING_PET_DOB, POSTING_PETBREED,
					null, POSTING_DESCRIPTION);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(posting);
		assertEquals(ErrorMessages.invalidPicture, error);
	}

	@Test
	public void testCreatePostingPictureEmpty() {
		String error = null;
		Posting posting = null;
		Client account = new Client();
		try {
			posting = service.createPosting(account, POSTING_DATE, POSTING_PETNAME, POSTING_PET_DOB, POSTING_PETBREED,
					" ", POSTING_DESCRIPTION);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(posting);
		assertEquals(ErrorMessages.invalidPicture, error);
	}

	@Test
	public void testCreatePostingDescriptionNull() {
		String error = null;
		Posting posting = null;
		Client account = new Client();
		try {
			posting = service.createPosting(account, POSTING_DATE, POSTING_PETNAME, POSTING_PET_DOB, POSTING_PETBREED,
					POSTING_PICTURE, null);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(posting);
		assertEquals(ErrorMessages.invalidReason, error);
	}

	@Test
	public void testCreatePostingDescriptionEmpty() {
		String error = null;
		Posting posting = null;
		Client account = new Client();
		try {
			posting = service.createPosting(account, POSTING_DATE, POSTING_PETNAME, POSTING_PET_DOB, POSTING_PETBREED,
					POSTING_PICTURE, "     ");
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(posting);
		assertEquals(ErrorMessages.invalidReason, error);
	}
	
	@Test
	public void testCreatePostingDescriptionLong() {
		String error = null;
		Posting posting = null;
		Client account = new Client();
		try {
			posting = service.createPosting(account, POSTING_DATE, POSTING_PETNAME, POSTING_PET_DOB, POSTING_PETBREED,
					POSTING_PICTURE, tooLongDescription);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(posting);
		assertEquals(ErrorMessages.invalidReason, error);
	}
	
	@Test
	public void testCreatePostingNotLoggedIn() {
		String error = null;
		Posting posting = null;
		Client account = new Client();
		account.setIsLoggedIn(false);
		try {
			posting = service.createPosting(account, POSTING_DATE, POSTING_PETNAME, POSTING_PET_DOB, POSTING_PETBREED,
					POSTING_PICTURE, POSTING_DESCRIPTION);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(posting);
		assertEquals(ErrorMessages.invalidLoggedIn, error);
	}
	
	@Test
	public void testCreatePostingSuccess() {
		Client account = new Client();
		account.setIsLoggedIn(true);
		account.setEmail(CLIENT_EMAIL_LOGGEDIN);
		try {
			Posting posting = service.createPosting(account, POSTING_DATE, POSTING_PETNAME, POSTING_PET_DOB, POSTING_PETBREED,
					POSTING_PICTURE, POSTING_DESCRIPTION);
			assertEquals(account.getEmail(),posting.getProfile().getEmail());
			assertEquals(POSTING_DATE,posting.getDate());
			assertEquals(POSTING_PETNAME,posting.getPetName());
			assertEquals(POSTING_PET_DOB,posting.getPetDateOfBirth());
			assertEquals(POSTING_PETBREED,posting.getPetBreed());
			assertEquals(POSTING_PICTURE,posting.getPicture());
			assertEquals(POSTING_DESCRIPTION,posting.getDescription());
			assertNull(posting.getApplication());
			assertNull(posting.getComment());	
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testUpdatePostingInfoPostingNull() {
		String error = null;
		Posting posting = null;
		Posting copyPosting = null;
		try {
			copyPosting = service.updatePostingInfo(posting, POSTING_PETNAME, POSTING_PET_DOB, POSTING_PETBREED,
					POSTING_PICTURE, POSTING_DESCRIPTION);
		}

		catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(copyPosting);
		assertEquals(ErrorMessages.invalidPosting, error);
	}
	
	@Test
	public void testUpdatePostingInfoPetNameNull() {
		String error = null;
		Posting posting = new Posting();
		Posting copyPosting = null;

		try {
			copyPosting = service.updatePostingInfo(posting, null, POSTING_PET_DOB, POSTING_PETBREED,
					POSTING_PICTURE, POSTING_DESCRIPTION);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(copyPosting);
		assertEquals(ErrorMessages.invalidPetName, error);
	}

	@Test
	public void testUpdatePostingInfoPetNameEmpty() {
		String error = null;
		Posting posting = new Posting();
		Posting copyPosting = null;

		try {
			copyPosting = service.updatePostingInfo(posting, "", POSTING_PET_DOB, POSTING_PETBREED,
					POSTING_PICTURE, POSTING_DESCRIPTION);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(copyPosting);
		assertEquals(ErrorMessages.invalidPetName, error);
	}

	@Test
	public void testUpdatePostingInfoPetNameWrong() {
		String error = null;
		Posting posting = new Posting();
		Posting copyPosting = null;

		try {
			copyPosting = service.updatePostingInfo(posting, invalidPetName, POSTING_PET_DOB, POSTING_PETBREED,
					POSTING_PICTURE, POSTING_DESCRIPTION);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(copyPosting);
		assertEquals(ErrorMessages.invalidPetName, error);
	}

	@Test
	public void testUpdatePostingInfoPetDOBNull() {
		String error = null;
		Posting posting = new Posting();
		Posting copyPosting = null;

		try {
			copyPosting = service.updatePostingInfo(posting, POSTING_PETNAME, null, POSTING_PETBREED,
					POSTING_PICTURE, POSTING_DESCRIPTION);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(copyPosting);
		assertEquals(ErrorMessages.invalidPetDOB, error);
	}

	@Test
	public void testUpdatePostingInfoPetDOBLater() {
		String error = null;
		Posting posting = new Posting();
		Posting copyPosting = null;
		Date later = new Date(timeFourMonthsFromNow);
		try {
			copyPosting = service.updatePostingInfo(posting, POSTING_PETNAME, later, POSTING_PETBREED,
					POSTING_PICTURE, POSTING_DESCRIPTION);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(copyPosting);
		assertEquals(ErrorMessages.invalidPetDOB, error);
	}

	@Test
	public void testUpdatePostingInfoPetBreedNull() {
		String error = null;
		Posting posting = new Posting();
		Posting copyPosting = null;
		try {
			copyPosting = service.updatePostingInfo(posting, POSTING_PETNAME, POSTING_PET_DOB, null,
					POSTING_PICTURE, POSTING_DESCRIPTION);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(copyPosting);
		assertEquals(ErrorMessages.invalidBreed, error);
	}

	@Test
	public void testUpdatePostingInfoPetBreedEmpty() {
		String error = null;
		Posting posting = new Posting();
		Posting copyPosting = null;
		try {
			copyPosting = service.updatePostingInfo(posting, POSTING_PETNAME, POSTING_PET_DOB, "",
					POSTING_PICTURE, POSTING_DESCRIPTION);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(copyPosting);
		assertEquals(ErrorMessages.invalidBreed, error);
	}

	@Test
	public void testUpdatePostingInfoPetBreedInvalid() {
		String error = null;
		Posting posting = new Posting();
		Posting copyPosting = null;
		try {
			copyPosting = service.updatePostingInfo(posting, POSTING_PETNAME, POSTING_PET_DOB, invalidBreed,
					POSTING_PICTURE, POSTING_DESCRIPTION);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(copyPosting);
		assertEquals(ErrorMessages.invalidBreed, error);
	}

	@Test
	public void testUpdatePostingInfoPictureNull() {
		String error = null;
		Posting posting = new Posting();
		Posting copyPosting = null;
		try {
			copyPosting = service.updatePostingInfo(posting, POSTING_PETNAME, POSTING_PET_DOB, POSTING_PETBREED,
					null, POSTING_DESCRIPTION);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(copyPosting);
		assertEquals(ErrorMessages.invalidPicture, error);
	}

	@Test
	public void testUpdatePostingInfoPictureEmpty() {
		String error = null;
		Posting posting = new Posting();
		Posting copyPosting = null;
		try {
			copyPosting = service.updatePostingInfo(posting, POSTING_PETNAME, POSTING_PET_DOB, POSTING_PETBREED,
					" ", POSTING_DESCRIPTION);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(copyPosting);
		assertEquals(ErrorMessages.invalidPicture, error);
	}

	@Test
	public void testUpdatePostingInfoDescriptionNull() {
		String error = null;
		Posting posting = new Posting();
		Posting copyPosting = null;
		try {
			copyPosting = service.updatePostingInfo(posting, POSTING_PETNAME, POSTING_PET_DOB, POSTING_PETBREED,
					POSTING_PICTURE, null);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(copyPosting);
		assertEquals(ErrorMessages.invalidReason, error);
	}

	@Test
	public void testUpdatePostingInfoDescriptionEmpty() {
		String error = null;
		Posting posting = new Posting();
		Posting copyPosting = null;
		try {
			copyPosting = service.updatePostingInfo(posting, POSTING_PETNAME, POSTING_PET_DOB, POSTING_PETBREED,
					POSTING_PICTURE, "     ");
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(copyPosting);
		assertEquals(ErrorMessages.invalidReason, error);
	}
	
	@Test
	public void testUpdatePostingInfoDescriptionLong() {
		String error = null;
		Posting posting = new Posting();
		Posting copyPosting = null;
		try {
			copyPosting = service.updatePostingInfo(posting, POSTING_PETNAME, POSTING_PET_DOB, POSTING_PETBREED,
					POSTING_PICTURE, tooLongDescription);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(copyPosting);
		assertEquals(ErrorMessages.invalidReason, error);
	}
	
	@Test
	public void testUpdatePostingInfoNotLoggedIn() {
		String error = null;
		Profile someone = new Client();
		someone.setIsLoggedIn(false);
		Posting posting = new Posting();
		posting.setProfile(someone);
		Posting copyPosting = null;
		
		try {
			copyPosting = service.updatePostingInfo(posting, POSTING_PETNAME, POSTING_PET_DOB, POSTING_PETBREED,
					POSTING_PICTURE, POSTING_DESCRIPTION);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(copyPosting);
		assertEquals(ErrorMessages.invalidLoggedIn, error);
	}
	
	@Test
	public void testUpdatePostingInfoSuccess() {
		Profile someone = new Client();
		someone.setIsLoggedIn(true);
		someone.setEmail(CLIENT_EMAIL_LOGGEDIN);
		Posting posting = new Posting();
		posting.setProfile(someone);
		try {
			Posting copyPosting = service.updatePostingInfo(posting, POSTING_PETNAME, POSTING_PET_DOB, POSTING_PETBREED,
					POSTING_PICTURE, POSTING_DESCRIPTION);
			assertEquals(copyPosting.getProfile().getEmail(),posting.getProfile().getEmail());
			assertEquals(POSTING_PETNAME,copyPosting.getPetName());
			assertEquals(POSTING_PET_DOB,copyPosting.getPetDateOfBirth());
			assertEquals(POSTING_PETBREED,copyPosting.getPetBreed());
			assertEquals(POSTING_PICTURE,copyPosting.getPicture());
			assertEquals(POSTING_DESCRIPTION,copyPosting.getDescription());
			assertNull(copyPosting.getApplication());
			assertNull(copyPosting.getComment());	
		} catch (Exception e) {
			fail();
		}
	}
	
	
	@Test
	public void testDeletePostingPostingNull() {
		String error = null;
		Posting posting = null;
		Posting copyPosting = null;
		try {
			copyPosting = service.deletePosting(posting);
		}

		catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(copyPosting);
		assertEquals(ErrorMessages.invalidPosting, error);
	}
	
	@Test
	public void testDeletePostingNotLoggedIn() {
		String error = null;
		Profile someone = new Client();
		someone.setIsLoggedIn(false);
		Posting posting = new Posting();
		posting.setProfile(someone);
		Posting copyPosting = null;
		
		try {
			copyPosting = service.deletePosting(posting);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(copyPosting);
		assertEquals(ErrorMessages.invalidLoggedIn, error);
	}
	
	@Test
	public void testDeletePostingSuccess() {
		Profile someone = new Client();
		someone.setIsLoggedIn(true);
		someone.setEmail(CLIENT_EMAIL_LOGGEDIN);
		Posting posting = new Posting();
		posting.setProfile(someone);
		posting.setApplication(null);
		posting.setComment(null);
		posting.setPetDateOfBirth(POSTING_PET_DOB);
		posting.setDescription(POSTING_DESCRIPTION);
		posting.setPetBreed(POSTING_PETBREED);
		posting.setPetName(POSTING_PETNAME);
		posting.setPicture(POSTING_PICTURE);
		try {
			Posting copyPosting = service.deletePosting(posting);
			assertEquals(copyPosting.getProfile().getEmail(),posting.getProfile().getEmail());
			assertEquals(POSTING_PETNAME,copyPosting.getPetName());
			assertEquals(POSTING_PET_DOB,copyPosting.getPetDateOfBirth());
			assertEquals(POSTING_PETBREED,copyPosting.getPetBreed());
			assertEquals(POSTING_PICTURE,copyPosting.getPicture());
			assertEquals(POSTING_DESCRIPTION,copyPosting.getDescription());
			assertNull(copyPosting.getApplication());
			assertNull(copyPosting.getComment());	
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testGetPosting() {
		Posting posting = null;
		try {
			posting = service.getPosting(CLIENT_EMAIL_LOGGEDIN, POSTING_DATE);
		} catch (Exception e) {
			// Check that no error occurred
			fail();
		}
		assertNotNull(posting);
		assertEquals(CLIENT_EMAIL_LOGGEDIN.hashCode() * POSTING_DATE.hashCode(), posting.getId());
	}
	
	// check that only postings that don't have an accepted application are returned
	@Test
	public void testGetOpenPostings() {

		List<Posting> openPostings = service.getOpenPostings();
		for (Posting posting : openPostings) {
			for (Application application : posting.getApplication()) {
				assertNotEquals(ApplicationStatus.accepted, application.getStatus());
			}
		}
	}

}
