package ca.mcgill.ecse321.petshelter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;
import static org.mockito.ArgumentMatchers.anyString;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
	private String CONTENT = "jierjrejee";

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
			message2.setDate(DATE);
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
	 * Checks if we can send a message with no content.
	 */
	@Test
	public void checkforLengthZero() {
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
			error = e.toString();
		}
		assertEquals(error,"You need to write a message before sending it.");
		
		
		


	}

}
