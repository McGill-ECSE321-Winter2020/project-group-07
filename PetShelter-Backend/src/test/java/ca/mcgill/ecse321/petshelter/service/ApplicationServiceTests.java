package ca.mcgill.ecse321.petshelter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
import ca.mcgill.ecse321.petshelter.model.ApplicationStatus;
import ca.mcgill.ecse321.petshelter.model.Client;
import ca.mcgill.ecse321.petshelter.model.HomeType;
import ca.mcgill.ecse321.petshelter.model.IncomeRange;
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
	private final static Posting VALID_POSTING = new Posting();
	private final static Posting INVALID_POSTING = new Posting();

	@BeforeEach
	public void setMockOutput() {
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

		lenient().when(applicationDAO.save(any(Application.class))).thenAnswer(returnParameterAsAnswer);   
	}


	//Tests for the getApplication() service method
	//Test Main Case Scenario
	@Test
	public void TestGetApplication() {
		Application application = null;
		try {
			application = service.getApplication(VALID_APPLICANT_EMAIL, VALID_POSTING);
		} catch (Exception e) {
			// Check that no error occurred
			fail();
		}
		assertNotNull(application);
		assertEquals(VALID_APPLICANT_EMAIL.hashCode() * VALID_POSTING.hashCode(), application.getId());		
	}

	@Test
	public void TestGetApplicationNullEmail() {
		Application application = null;
		String error = null;
		try {
			application = service.getApplication(null, VALID_POSTING);
		} catch (Exception e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		assertNull(application);
		assertEquals(ErrorMessages.invalidEmail, error);
	}

	@Test
	public void TestGetApplicationNullPosting() {
		Application application = null;
		String error = null;
		try {
			application = service.getApplication(VALID_APPLICANT_EMAIL, null);
		} catch (Exception e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		assertNull(application);
		assertEquals(ErrorMessages.invalidPosting, error);
	}

	@Test
	public void TestGetApplicationNullApplication() {
		Application application = null;
		String error = null;
		try {
			application = service.getApplication(VALID_APPLICANT_EMAIL, INVALID_POSTING);
		} catch (Exception e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		assertNull(application);
		assertEquals(ErrorMessages.applicationDoesNotExist, error);
	}

	//Tests for the getPostingApplications() service method
	//Test Main Case scenario
	@Test
	public void TestGetPostingApplications() {
		Posting posting = new Posting();
		HashSet<Application> applications = new HashSet<>();
		
		Application app1 = new Application();
		applications.add(app1);
		Application app2 = new Application();
		applications.add(app2);
		Application app3 = new Application();
		applications.add(app3);
		Application app4 = new Application();
		applications.add(app4);
	
		posting.setApplication(applications);
		List<Application> appsList = service.toList(applications);
		
		List<Application> returnedList = null;
		
		try {
			returnedList = service.getPostingApplications(posting);
		} catch (Exception e) {
			// Check no errors have occurred
			fail();
		}
		
		assertNotNull(returnedList);
		assertEquals(appsList, returnedList);
	}
	
	@Test
	public void TestGetPostingApplicationsNullPosting() {
		String error = null;
		ArrayList<Application> applications = null;
		try {
			applications = (ArrayList<Application>) service.getPostingApplications(null);
		} catch (Exception e) {
			error = e.getMessage();
		}
		
		assertNull(applications);
		assertEquals(ErrorMessages.invalidPosting, error);
	}
	
	//Tests for the rejectApplication() service method
	//Test Main Case scenario
	@Test
	public void TestRejectApplication() {
		Application application = new Application();
		application.setStatus(ApplicationStatus.pending);
		try {
			application = service.rejectApplication(application);
		} catch (Exception e) {
			// Check that no error ocurred
			fail();
		}
		assertNotNull(application);
		assertEquals(ApplicationStatus.rejected, application.getStatus());
	}
	
	@Test
	public void TestRejectApplicationNullApplication() {
		String error = null;
		try {
			service.rejectApplication(null);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertEquals(ErrorMessages.invalidApplication, error);
	}
	
	@Test
	public void TestRejectApplicationApprovedApplication() {
		String error = null;
		Application application = new Application();
		application.setStatus(ApplicationStatus.accepted);
		try {
			service.rejectApplication(application);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertEquals(ErrorMessages.rejectingApprovedApp, error);
	}
	
	//Tests for the approveApplication() service method
	//Test Main Case scenario
	@Test
	public void TestApproveApplication() {
		Posting posting = new Posting();
		HashSet<Application> applications = new HashSet<Application>();

		Application toApprove = new Application();
		toApprove.setStatus(ApplicationStatus.pending);
		toApprove.setPosting(posting);
		applications.add(toApprove);

		Application otherApp1 = new Application();
		otherApp1.setStatus(ApplicationStatus.pending);
		otherApp1.setPosting(posting);
		applications.add(otherApp1);
		
		Application otherApp2 = new Application();
		otherApp2.setStatus(ApplicationStatus.pending);
		otherApp2.setPosting(posting);
		applications.add(otherApp2);
		
		posting.setApplication(applications);
		
		try {
			toApprove = service.approveApplication(toApprove);
		} catch (Exception e) {
			// Check that no error ocurred
			fail();
		}
		assertNotNull(toApprove);
		assertEquals(ApplicationStatus.accepted, toApprove.getStatus());
		assertEquals(ApplicationStatus.rejected, otherApp1.getStatus());
		assertEquals(ApplicationStatus.rejected, otherApp2.getStatus());
	}
	
	@Test
	public void TestApproveApplicationNullApplication() {
		String error = null;
		try {
			service.approveApplication(null);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertEquals(ErrorMessages.invalidApplication, error);
	}
	
	@Test
	public void TestRejectApplicationNotPendingApplication() {
		String error = null;
		Application application = new Application();
		application.setStatus(ApplicationStatus.rejected);
		try {
			service.approveApplication(application);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertEquals(ErrorMessages.notPendingApp, error);
		
		error = null;
		application.setStatus(ApplicationStatus.accepted);
		try {
			service.approveApplication(application);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertEquals(ErrorMessages.notPendingApp, error);
		
	}
	
	//Tests for the createApplication() service method
	//Test Main Case scenario
	@Test
	public void TestCreateApplication() {
		Client owner = new Client();
		owner.setEmail(VALID_OWNER_EMAIL);
		Posting posting = new Posting();
		posting.setProfile(owner);
		posting.setId(123456);

		Client applicant = new Client();
		applicant.setEmail(VALID_APPLICANT_EMAIL);

		try {
			Application application = service.createApplication(applicant, posting, HomeType.apartment, IncomeRange.low, 2);
			assertEquals(VALID_APPLICANT_EMAIL, application.getClient().getEmail());
			assertEquals(posting, application.getPosting());
			assertEquals(HomeType.apartment.toString(), application.getHomeType().toString());
			assertEquals(IncomeRange.low.toString(), application.getIncomeRange().toString());			
			assertEquals(2, application.getNumberOfResidents());
			assertEquals(ApplicationStatus.pending, application.getStatus());
			
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void TestCreateApplicationNullClient() {
		String error = null;
		Posting posting = new Posting();
		Application application = null;
		try {
			application = service.createApplication(null, posting, HomeType.apartment, IncomeRange.low, 2);
		} catch (Exception e) {
			error = e.getMessage();
		} 
		
		assertNull(application);
		assertEquals(ErrorMessages.accountDoesNotExist, error);
	}
	
	@Test
	public void TestCreateApplicationNullPosting() {
		String error = null;
		Client client = new Client();
		Application application = null;
		try {
			application = service.createApplication(client, null, HomeType.apartment, IncomeRange.low, 2);
		} catch (Exception e) {
			error = e.getMessage();
		} 
		
		assertNull(application);
		assertEquals(ErrorMessages.invalidPosting, error);
	}
	
	@Test
	public void TestCreateApplicationSelfApplication() {
		String error = null;
		
		//Test the case where the client applies to their own post 
		Client client = new Client();
		client.setEmail(VALID_APPLICANT_EMAIL);
		Posting posting = new Posting();
		posting.setProfile(client);
		Application application = null;
		
		try {
			application = service.createApplication(client, posting, HomeType.apartment, IncomeRange.low, 1);
		} catch (Exception e) {
			error = e.getMessage();
		} 
		
		assertNull(application);
		assertEquals(ErrorMessages.selfApplication, error);
	}
	
	@Test
	public void TestCreateApplicationNullHomeType() {
		String error = null;
		Application application = null;
		
		Client owner = new Client();
		owner.setEmail(VALID_OWNER_EMAIL);
		Posting posting = new Posting();
		posting.setProfile(owner);

		Client applicant = new Client();
		applicant.setEmail(VALID_APPLICANT_EMAIL);
		
		try {
			application = service.createApplication(applicant, posting, null, IncomeRange.low, 2);
		} catch (Exception e) {
			error = e.getMessage();
		} 
		
		assertNull(application);
		assertEquals(ErrorMessages.invalidHomeType, error);
	}
	
	@Test
	public void TestCreateApplicationNullIncomeRange() {
		String error = null;
		Application application = null;
		
		Client owner = new Client();
		owner.setEmail(VALID_OWNER_EMAIL);
		Posting posting = new Posting();
		posting.setProfile(owner);

		Client applicant = new Client();
		applicant.setEmail(VALID_APPLICANT_EMAIL);
		
		try {
			application = service.createApplication(applicant, posting, HomeType.apartment, null, 2);
		} catch (Exception e) {
			error = e.getMessage();
		} 
		
		assertNull(application);
		assertEquals(ErrorMessages.invalidIncomeRange, error);
	}
	
	@Test
	public void TestCreateApplicationZeroResidents() {
		String error = null;
		Application application = null;
		
		Client owner = new Client();
		owner.setEmail(VALID_OWNER_EMAIL);
		Posting posting = new Posting();
		posting.setProfile(owner);

		Client applicant = new Client();
		applicant.setEmail(VALID_APPLICANT_EMAIL);
		
		try {
			application = service.createApplication(applicant, posting, HomeType.apartment, IncomeRange.low, 0);
		} catch (Exception e) {
			error = e.getMessage();
		} 
		
		assertNull(application);
		assertEquals(ErrorMessages.invalidNOR, error);
	}

	@Test
	public void TestCreateApplicationNegativeResidents() {
		String error = null;
		Application application = null;
		
		Client owner = new Client();
		owner.setEmail(VALID_OWNER_EMAIL);
		Posting posting = new Posting();
		posting.setProfile(owner);

		Client applicant = new Client();
		applicant.setEmail(VALID_APPLICANT_EMAIL);
		
		try {
			application = service.createApplication(applicant, posting, HomeType.apartment, IncomeRange.low, -5);
		} catch (Exception e) {
			error = e.getMessage();
		} 
		
		assertNull(application);
		assertEquals(ErrorMessages.invalidNOR, error);
	}	
}



