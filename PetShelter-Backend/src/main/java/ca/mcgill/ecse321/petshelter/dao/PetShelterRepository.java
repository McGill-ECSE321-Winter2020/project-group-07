package ca.mcgill.ecse321.petshelter.dao;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.petshelter.model.*;


@Repository
public class PetShelterRepository {

	@Autowired
	EntityManager entityManager;

	@Transactional
	public Admin createAdmin(String email, String address, Set<Comment> comments, Date birthdate, Set<Message> messages, String password,int phoneNumber, Set<Posting> postings) {
		Admin p = new Admin();
		p.setAddress(address);
		p.setComments(comments);
		p.setDate(birthdate);
		p.setMessages(messages);
		p.setPassword(password);
		p.setPhoneNumber(phoneNumber);
		p.setPostings(postings);
		p.setEmail(email);
		entityManager.persist(p);
		return p;
	}

	@Transactional
	public Admin getAdmin(String email) {
		Admin p = entityManager.find(Admin.class, email);
		return p;
	}
	@Transactional
	public Client createClient(String email, String address, ArrayList<Application> applications, Set<Comment> comments, Date birthdate, ArrayList<Donation> donations, Set<Message> messages, String password,int phoneNumber, Set<Posting> postings) {
		Client p = new Client();
		p.setAddress(address);
		p.setApplications(applications);
		p.setComments(comments);
		p.setDate(birthdate);
		p.setDonations(donations);
		p.setMessages(messages);
		p.setPassword(password);
		p.setPhoneNumber(phoneNumber);
		p.setPostings(postings);
		p.setEmail(email);
		entityManager.persist(p);
		return p;
	}

	@Transactional
	public Client getClient(String email) {
		Client p = entityManager.find(Client.class, email);
		return p;
	}

	@Transactional
	public Event createEvent(String name, Date date, Time startTime, Time endTime) {
		Event e = new Event();
		e.setName(name);
		e.setDate(date);
		e.setStartTime(startTime);
		e.setEndTime(endTime);
		entityManager.persist(e);
		return e;
	}

	@Transactional
	public Event getEvent(String name) {
		Event e = entityManager.find(Event.class, name);
		return e;
	}

}