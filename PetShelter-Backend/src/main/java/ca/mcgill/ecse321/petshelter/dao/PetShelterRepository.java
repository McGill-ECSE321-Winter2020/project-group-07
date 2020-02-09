package ca.mcgill.ecse321.petshelter.dao;

import java.sql.Date;

import java.util.*;

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
	public Admin createAdmin(String email, String address, Set<Comment> comments, Date birthdate, Set<Message> messages, String password,String phoneNumber, Set<Posting> postings) {
		Admin p = new Admin();
		p.setAddress(address);
		p.setComments(comments);
		p.setDateOfBirth(birthdate);
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
	public Client createClient(String email, String address, Set<Application> applications, Set<Comment> comments, Date birthdate, Set<Donation> donations, Set<Message> messages, String password,String phoneNumber, Set<Posting> postings) {
		Client p = new Client();
		p.setAddress(address);
		p.setApplications(applications);
		p.setComments(comments);
		p.setDateOfBirth(birthdate);
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
	public Application createApplication(Client client, HomeType homeType, Integer id,IncomeRange incomeRange,Integer numberOfResidents,Posting posting,ApplicationStatus status) {
		Application a = new Application();
		a.setClient(client);
		a.setHomeType(homeType);
		a.setId(id);
		a.setIncomeRange(incomeRange);
		a.setNumberOfResidents(numberOfResidents);
		a.setPosting(posting);
		a.setStatus(status);
		entityManager.persist(a);
		return a;
	}
	
	@Transactional
	public Application getApplication(Integer id) {
		Application a = entityManager.find(Application.class, id);
		return a;
	}
	
	@Transactional
	public Comment createComment(String content,Date date, Integer id,Posting posting,Profile profile) {
		Comment c = new Comment();
		c.setContent(content);
		c.setDate(date);
		c.setId(id);
		c.setPosting(posting);
		c.setProfile(profile);
		entityManager.persist(c);
		return c;
	}
	
	@Transactional
	public Comment getComment(Integer id) {
		Comment c = entityManager.find(Comment.class, id);
		return c;
	}
	
	@Transactional
	public Donation createDonation(Integer amount, Client client,Date date,Integer id) {
		Donation d = new Donation();
		d.setAmount(amount);
		d.setClient(client);
		d.setDate(date);
		d.setId(id);
		entityManager.persist(d);
		return d;
		
	}
	
	@Transactional
	public Donation getDonation(Integer id) {
		Donation d = entityManager.find(Donation.class, id);
		return d;
	}
	
	@Transactional
	public Message createMessage(Admin admin,Client client,String content,Date date, Integer id) {
		Message m = new Message();
		m.setAdmin(admin);
		m.setClient(client);
		m.setContent(content);
		m.setDate(date);
		m.setId(id);
		entityManager.persist(m);
		return m;
	}
	
	@Transactional
	public Message getMessage(Integer id) {
		Message m = entityManager.find(Message.class, id);
		return m;
	}

	
	@Transactional
	public Posting createPosting(Set<Application> applications,Set<Comment> comments,Date date,String description, Integer id,String breed,Date petDateBirth,String petName,String picture,Profile profile) {
		Posting p = new Posting();
		p.setApplication(applications);
		p.setComment(comments);
		p.setDate(date);
		p.setDescription(description);
		p.setId(id);
		p.setPetBreed(breed);
		p.setPetDateOfBirth(petDateBirth);
		p.setPetName(petName);
		p.setPicture(picture);
		p.setProfile(profile);
		entityManager.persist(p);
		return p;
	}
	
	@Transactional
	public Posting getPosting(Integer id) {
		Posting p = entityManager.find(Posting.class, id);
		return p;
	}
}