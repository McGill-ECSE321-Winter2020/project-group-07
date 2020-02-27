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
	
	// Dummy client attributes
	
	
	//Dummy admin attributes
	
	
	//Dummy posting attributes
	
	
	//Dummy illegitimate attributes for creating a new comment
	
	// Test stubs
	@BeforeEach
	public void setMockOutput() {
		
	}
	
	
}
