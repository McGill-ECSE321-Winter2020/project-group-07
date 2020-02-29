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

	@BeforeEach
	public void setMockOutput() {
		lenient().when(service.toList(messageDao.findAll())).thenAnswer((InvocationOnMock invocation) -> {

			ArrayList<Message> messages = new ArrayList<>();
			Message message1 = new Message();
			Message message2 = new Message();
			Client client1 = new Client();
			Client client2 = new Client();
			client1.setEmail(EMAIL);
			client2.setEmail(EMAIL);
			message1.setClient(client1);
			message2.setClient(client2);
			Admin admin = new Admin();
			message1.setAdmin(admin);
			message2.setAdmin(admin);
			message1.setContent(CONTENT);
			message2.setContent(CONTENT);
			message1.setDate(DATE);
			message2.setDate(DOB);
			messages.add(message1);
			messages.add(message2);
			return messages;
		});

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
		System.out.println(cont);
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
	 * checks if the client is spamming the admin.
	 */
	@Test
	public void checkForSpamming() {
		String content = "a";
		String email = EMAIL;
		String aemail = ADMIN_EMAIL;
		Date date = DATE;
		Date date1 = DATE1;
		Client client = new Client();
		client.setEmail(email);
		client.setDateOfBirth(DOB);
		Admin admin = new Admin();
		service.sendMessage(admin,client, content, date);
		admin.setEmail(aemail);
		String error = "";
		try {
			service.sendMessage(admin,client, content, date1);
		}catch(Exception e) {
			error = e.getMessage();
		}
		assertEquals(error,"The message you are trying to send is identical to a previous message already sent.");
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

}
