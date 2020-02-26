package ca.mcgill.ecse321.petshelter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;

import java.sql.Date;

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
import ca.mcgill.ecse321.petshelter.model.Application;
import ca.mcgill.ecse321.petshelter.model.Posting;


@ExtendWith(MockitoExtension.class)
public class ApplicationServiceTests {

	@Mock
	private ApplicationRepository applicationDAO;

	@InjectMocks
	private PetShelterService service;

	//Testing variables
	private final static String VALID_APPLICANT_EMAIL = "applicant@petshelter.com";
	private final static String VALID_OWNER_EMAIL = "owner@petshelter.com";
	private final static Date VALID_DATE = Date.valueOf("2000-01-01");
	private final static Posting VALID_POSTING = new Posting();

	private final static String OWNER_NO_POSTINGS = "nopostings@petshelter.com";
	private final static Posting INVALID_POSTING = new Posting();


	@BeforeEach
	public void setMockOutput() {
		//getPosting is not being tested in this class, always return a valid Posting object
		lenient().when(service.getPosting(anyString(), any(Date.class))).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(VALID_OWNER_EMAIL) && invocation.getArgument(1).equals(VALID_DATE)) {
				return VALID_POSTING;				
			}
			else {
				return INVALID_POSTING;
			}
		});
		//test stub for findApplicationById() CRUD method
		lenient().when(applicationDAO.findApplicationById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(VALID_APPLICANT_EMAIL.hashCode() * VALID_POSTING.hashCode())) {
				Application application = new Application();
				application.setId(VALID_APPLICANT_EMAIL.hashCode() * VALID_POSTING.hashCode());
				return application;
			}
			else {
				return null;
			}
		});
		// Whenever anything is saved, just return the parameter object
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};

		//TODO: add mock for applicationDAO.save()   
	}


	//Tests for the getApplication() service method

	//Test Main Case Scenario
	@Test
	public void TestGetApplication() {
		Application application = null;
		try {
			application = service.getApplication(VALID_APPLICANT_EMAIL, VALID_OWNER_EMAIL, VALID_DATE);
		} catch (Exception e) {
			// Check that no error occurred
			fail();
		}
		assertNotNull(application);
		assertEquals(VALID_APPLICANT_EMAIL.hashCode() * VALID_POSTING.hashCode(), application.getId());		
	}

	@Test
	public void TestGetApplicationNullEmails() {
		Application application = null;
		String error = null;
		try {
			application = service.getApplication(null, null, VALID_DATE);
		} catch (Exception e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		assertNull(application);
		assertEquals(ErrorMessages.invalidEmail, error);
	}

	@Test
	public void TestGetApplicationNullDate() {
		Application application = null;
		String error = null;
		try {
			application = service.getApplication(VALID_APPLICANT_EMAIL, VALID_OWNER_EMAIL, null);
		} catch (Exception e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		assertNull(application);
		assertEquals(ErrorMessages.invalidDate, error);
	}

	@Test
	public void TestGetApplicationNullApplication() {
		Application application = null;
		String error = null;
		try {
			application = service.getApplication(VALID_APPLICANT_EMAIL, OWNER_NO_POSTINGS, VALID_DATE);
		} catch (Exception e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		assertNull(application);
		assertEquals(ErrorMessages.applicationDoesNotExist, error);
	}

	//Tests for the rejectApplication() service method
	//Tests for the approveApplication() service method
	//Tests for the createApplication() service method
}


