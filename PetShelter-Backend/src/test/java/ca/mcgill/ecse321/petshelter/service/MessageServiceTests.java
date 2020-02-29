package ca.mcgill.ecse321.petshelter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;
import static org.mockito.ArgumentMatchers.anyString;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.mapping.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.petshelter.dao.*;
import ca.mcgill.ecse321.petshelter.model.*;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTests {

	@Mock
	private MessageRepository messageDao;
	@InjectMocks
	private PetShelterService service;
	//checking for empty content
	private String EMPTY_CONTENT="";
	private String EMAIL = "joohn.doe@mail.com";
	private String ADMIN_EMAIL = "admin.admin@admin.com";
	private Date DATE = Date.valueOf("2005-05-01");
	private Date DOB = Date.valueOf("2004-05-01");
	private String CONTENT = "jierjrejee";
	private Date DATE1 = Date.valueOf("2005-05-02");
	private Date BEF_DOB = Date.valueOf("2002-05-01");
	private Client CLIENT = new Client();

	@BeforeEach
	public void setMockOutput() {
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};

		lenient().when(messageDao.save(any(Message.class))).thenAnswer(returnParameterAsAnswer);

	}

	/**
	 * Checks if we can send a message with more than 1000 characters.
	 */
	@Test
	public void checkforLengthZero() {
		String cont = "a";
		String email = EMAIL;
		String aemail = ADMIN_EMAIL;
		Date date = DATE;
		Client client = new Client();
		client.setEmail(email);
		Admin admin = new Admin();
		admin.setEmail(aemail);
		int i = 0;
		String string = "a";
		for(i =0; i<1010; i++) {
			cont = cont + string;
		}
		String error = "";
		try {
			service.sendMessage(admin,client, cont, date);
		}catch(Exception e) {
			error = e.getMessage();
		}
		assertEquals(error,"Your message is too long.");
	}

	/**
	 * Tests if the message is created if it has no content.
	 */
	@Test
	public void checkForLengthGreater() {
		String noCont = EMPTY_CONTENT;
		String email = EMAIL;
		String aemail = ADMIN_EMAIL;
		Date date = DATE;
		Client client = new Client();
		client.setEmail(email);
		Admin admin = new Admin();
		admin.setEmail(aemail);
		String error = "";
		try {
			service.sendMessage(admin,client, noCont, date);
		}catch(Exception e) {
			error = e.getMessage();
		}
		assertEquals(error,"You need to write a message before sending it.");
	}

	/**
	 * check if the message is sent even though the date is null.
	 */
	@Test
	public void checkForDateNull() {
		String content = "a";
		String email = EMAIL;
		String aemail = ADMIN_EMAIL;
		Date date = null;
		Client client = new Client();
		client.setEmail(email);
		Admin admin = new Admin();
		admin.setEmail(aemail);
		String error = "";
		try {
			service.sendMessage(admin,client, content, date);
		}catch(Exception e) {
			error = e.getMessage();
		}
		assertEquals(error,"No date for message.");
	}

	/**
	 * Checks if message is sent if client is null.
	 */
	@Test
	public void checkForClientNull() {
		String content = "a";
		String email = EMAIL;
		String aemail = ADMIN_EMAIL;
		Date date = DATE;
		Client client = new Client();
		client = null;
		Admin admin = new Admin();
		admin.setEmail(aemail);
		String error = "";
		try {
			service.sendMessage(admin,client, content, date);
		}catch(Exception e) {
			error = e.getMessage();
		}
		assertEquals(error,"Account does not exist.");
	}

	/**
	 * Tests if a message can be sent. 
	 */
	@Test
	public void checkForAdminNull() {
		String content = "a";
		String email = EMAIL;
		String aemail = ADMIN_EMAIL;
		Date date = DATE;
		Client client = new Client();
		client.setEmail(email);
		Admin admin = new Admin();
		admin = null;
		String error = "";
		try {
			service.sendMessage(admin,client, content, date);
		}catch(Exception e) {
			error = e.getMessage();
		}
		assertEquals(error,"The admin does not seem to exist.");
	}

	/**
	 * test if the client is trying to send a message with a date before
	 * its date of birth.
	 */
	@Test
	public void checkForIllegalDate() {
		String content = "a";
		String email = EMAIL;
		String aemail = ADMIN_EMAIL;
		Date dob = DOB;
		Date date1 = BEF_DOB;
		Client client = new Client();
		client.setEmail(email);
		client.setDateOfBirth(dob);
		Admin admin = new Admin();
		admin.setEmail(aemail);
		String error = "";
		try {
			service.sendMessage(admin,client, content, date1);
		}catch(Exception e) {
			error = e.getMessage();
		}
		assertEquals(error,"The date specified is before the date of birth of the client.");
	}

	@Test
	public void getListOfExistingClient() {
		Client client = CLIENT;
		String email = EMAIL;

		client.setEmail(email);
		Message message = new Message();
		message.setContent(CONTENT);
		Set<Message> init = new HashSet<>();
		message.setClient(client);
		message.setDate(DATE);
		init.add(message);
		client.setMessages(init);
		List<Message> messages = null;
		try {
			messages = service.getClientMessages(client);
		}catch(Exception e) {
			String error = e.getMessage();
		}
		assertEquals("jierjrejee",messages.get(0).getContent());
	}

	/**
	 * checks if success sendMessage from service.
	 */
	@Test
	public void checkIfSuccessSendMessage() {
		String cont = "a";
		String email = EMAIL;
		String aemail = ADMIN_EMAIL;
		Date date = DATE;
		Client client = new Client();
		client.setEmail(email);
		client.setDateOfBirth(DOB);
		Admin admin = new Admin();
		admin.setEmail(aemail);
		String error = "";
		Message message = new Message();
		try {
			message = service.sendMessage(admin,client, cont, date);
		}catch(Exception e) {
			error = e.getMessage();
		}
		assertEquals("a",message.getContent());
	}

	/**
	 * checks if can get list of messages with null client.
	 */
	@Test
	public void checkGetMessagesClientNull() {
		String content = "a";
		String email = EMAIL;
		String aemail = ADMIN_EMAIL;
		Date date = DATE;
		Client client = new Client();
		client = null;
		Admin admin = new Admin();
		admin.setEmail(aemail);
		String error = "";
		try {
			service.getClientMessages(client);
		}catch(Exception e) {
			error = e.getMessage();
		}
		assertEquals(error,"Account does not exist.");
	}

	/**
	 * checks if the can get a list of messages from a client that has no messages.
	 */
	@Test
	public void checkGetMessagesClientHasNoMessages() {

		String cont = "a";
		String email = EMAIL;
		String aemail = ADMIN_EMAIL;
		Date date = DATE;
		Client client = new Client();
		client.setEmail(email);
		client.setDateOfBirth(DOB);
		Admin admin = new Admin();
		admin.setEmail(aemail);
		String error = "";
		List<Message> message =null;
		try {
			message = service.getClientMessages(client);
		}catch(Exception e) {
			error = e.getMessage();
		}
		assertEquals("This client did not send any messages yet.",error);		
	}
}
