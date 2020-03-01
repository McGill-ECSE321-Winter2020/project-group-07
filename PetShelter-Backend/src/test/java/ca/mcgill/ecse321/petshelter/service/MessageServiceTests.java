package ca.mcgill.ecse321.petshelter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

import java.sql.Date;
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
import ca.mcgill.ecse321.petshelter.dao.MessageRepository;
import ca.mcgill.ecse321.petshelter.model.Admin;
import ca.mcgill.ecse321.petshelter.model.Client;
import ca.mcgill.ecse321.petshelter.model.Message;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTests {

	@Mock
	private MessageRepository messageDao;
	@InjectMocks
	private PetShelterService service;
	
	private final static String VALID_EMAIL = "pet.lover@mail.com";
	private final static String EMPTY_CONTENT = "    ";
	private final static String VALID_CONTENT = "I would like to adopt a pet";
	private final static String TOO_LONG_CONTENT = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nu";
	private final static Date VALID_DATE = Date.valueOf("2005-05-01");
	private final static Date DOB = Date.valueOf("2004-05-01");
	private final static Date BEF_DOB = Date.valueOf("2002-05-01");

	@BeforeEach
	public void setMockOutput() {
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};

		lenient().when(messageDao.save(any(Message.class))).thenAnswer(returnParameterAsAnswer);

	}

	
	//Tests for the sendMessage() method 
	
	//Test the main case scenario
	@Test
	public void testSendMessage() {
		Client client = new Client();
		client.setEmail(VALID_EMAIL);
		client.setDateOfBirth(DOB);
		client.setIsLoggedIn(true);
		Admin admin = new Admin();
		Message message = null;
		try {
			message = service.sendMessage(admin, client, VALID_CONTENT, VALID_DATE);
			
			assertNotNull(message);
			assertEquals(VALID_CONTENT, message.getContent());
		} catch (Exception e) {
			//e.printStackTrace();
			fail();
		}

	}
	
	@Test
	public void testSendMessageNullContent() {
		Client client = new Client();
		client.setIsLoggedIn(true);
		Admin admin = new Admin();
		Message message = null;
		
		String error = null;
		try {
			message = service.sendMessage(admin, client, null, VALID_DATE);
		} catch (Exception e) {
			error = e.getMessage();
		}
		
		assertNull(message);
		assertEquals(ErrorMessages.NoContent, error);
	}
	
	@Test
	public void testSendMessageEmptyContent() {
		Client client = new Client();
		client.setIsLoggedIn(true);
		Admin admin = new Admin();
		Message message = null;
		
		String error = null;
		try {
			message = service.sendMessage(admin, client, EMPTY_CONTENT, VALID_DATE);
		} catch (Exception e) {
			error = e.getMessage();
		}
		
		assertNull(message);
		assertEquals(ErrorMessages.NoContent, error);
	}
	
	@Test
	public void testSendMessageTooLong() {
		Client client = new Client();
		client.setIsLoggedIn(true);
		Admin admin = new Admin();
		Message message = null;
		
		String error = null;
		try {
			message = service.sendMessage(admin, client, TOO_LONG_CONTENT, VALID_DATE);
		} catch (Exception e) {
			error = e.getMessage();
		}
		
		assertNull(message);
		assertEquals(ErrorMessages.tooLong, error);
	}
	
	@Test
	public void testSendMessageNullDate() {
		Client client = new Client();
		client.setIsLoggedIn(true);
		Admin admin = new Admin();
		Message message = null;
		
		String error = null;
		try {
			message = service.sendMessage(admin, client, VALID_CONTENT, null);
		} catch (Exception e) {
			error = e.getMessage();
		}
		
		assertNull(message);
		assertEquals(ErrorMessages.invalidDate, error);
	}
	
	@Test
	public void testSendMessageNullClient() {
		Admin admin = new Admin();
		Message message = null;
		
		String error = null;
		try {
			message = service.sendMessage(admin, null, VALID_CONTENT, VALID_DATE);
		} catch (Exception e) {
			error = e.getMessage();
		}
		
		assertNull(message);
		assertEquals(ErrorMessages.accountDoesNotExist, error);
	}
	
	@Test
	public void testSendMessageLoggedOutClient() {
		Client client = new Client();
		client.setIsLoggedIn(false);
		Admin admin = new Admin();
		Message message = null;
		
		String error = null;
		try {
			message = service.sendMessage(admin, client, VALID_CONTENT, VALID_DATE);
		} catch (Exception e) {
			error = e.getMessage();
		}
		
		assertNull(message);
		assertEquals(ErrorMessages.notLoggedIn, error);
	}

	@Test
	public void testSendMessageNullAdmin() {
		Client client = new Client();
		client.setIsLoggedIn(true);
		Message message = null;
		
		String error = null;
		try {
			message = service.sendMessage(null, client, VALID_CONTENT, VALID_DATE);
		} catch (Exception e) {
			error = e.getMessage();
		}
		
		assertNull(message);
		assertEquals(ErrorMessages.IncorrectAdmin, error);
	}
	
	@Test
	public void testSendMessageDateBeforeDOB() {
		Client client = new Client();
		client.setDateOfBirth(DOB);
		client.setIsLoggedIn(true);
		Admin admin = new Admin();
		Message message = null;
		
		String error = null;
		try {
			message = service.sendMessage(admin, client, VALID_CONTENT, BEF_DOB);
		} catch (Exception e) {
			error = e.getMessage();
		}
		
		assertNull(message);
		assertEquals(ErrorMessages.DateBefDOB, error);
	}
	
	
	//Tests for the getClientMessages() method 
	
	//Test the main case scenario
	@Test 
	public void testGetClientMessages() {
		Client client = new Client();
		client.setIsLoggedIn(true);
		HashSet<Message> messages = new HashSet<Message>();
		Message m1 = new Message();
		messages.add(m1);
		Message m2 = new Message();
		messages.add(m2);
		Message m3 = new Message();
		messages.add(m3);
		client.setMessages(messages);
		
		List<Message> returnedMessages = null;
		try {
			returnedMessages = service.getClientMessages(client);
			assertNotNull(returnedMessages);
			assertEquals(service.toList(messages), returnedMessages);
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test 
	public void testGetClientMessagesNullClient() {
		List<Message> returnedMessages = null;
		String error = null;
		try {
			returnedMessages = service.getClientMessages(null);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(returnedMessages);
		assertEquals(ErrorMessages.accountDoesNotExist, error);		
	}
	
	@Test 
	public void testGetClientMessagesLoggedOutClient() {
		Client client = new Client();
		client.setIsLoggedIn(false);
		
		List<Message> returnedMessages = null;
		String error = null;
		try {
			returnedMessages = service.getClientMessages(client);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(returnedMessages);
		assertEquals(ErrorMessages.notLoggedIn, error);		
	}
	
	@Test 
	public void testGetClientMessagesNullMessages() {
		Client client = new Client();
		client.setIsLoggedIn(true);
		HashSet<Message> messages = new HashSet<Message>();
		client.setMessages(messages);
		
		List<Message> returnedMessages = null;
		String error = null;
		try {
			returnedMessages = service.getClientMessages(client);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(returnedMessages);
		assertEquals(ErrorMessages.ClientHasNoMessages, error);		
	}
}
