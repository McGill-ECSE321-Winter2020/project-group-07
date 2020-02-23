package ca.mcgill.ecse321.petshelter.dto;

import ca.mcgill.ecse321.petshelter.model.*;

import java.sql.Date;
import java.util.List; 

public class ClientDTO {

    // Profile attributes
    private Date dateOfBirth; 
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private List<Posting> postings; 
    private List<Comment> comments; 

    // Client attributes
    private String firstName; 
    private String lastName; 
    private List<Donation> donations;
    private List<Message> messages;
    private List<Application> applications; 

    // Can add more constructors later based on functionality needed
    public ClientDTO() {
    }

    // Viewing your own profile
    public ClientDTO(Date dob, String email, String phoneNumber, String address, List<Posting> postings, 
                     List<Comment> comments, String firstName, String lastName, List<Donation> donations, 
                     List<Message> messages, List<Application> applications) {
        this.dateOfBirth = dob;
        this.email = email; // Might get complicated changing the primary key in DB
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.postings = postings;
        this.comments = comments; 
        this.firstName = firstName;
        this.lastName = lastName; 
        this.donations = donations;
        this.messages = messages;
        this.applications = applications; 
    }

    // Updating information
    public ClientDTO(Date dob, String password, String phoneNumber, 
                     String address, String firstName, String lastName) {
        this.dateOfBirth = dob;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.firstName = firstName;
        this.lastName = lastName; 
    }

    // Viewing others profile
    public ClientDTO(Date dob, String email, String firstName, String lastName, List<Posting> postings) {
        this.dateOfBirth = dob; 
        this.email = email;
        this.firstName = firstName; 
        this.lastName = lastName; 
        this.postings = postings;
    }

    // The person who posted your accepted application
    public ClientDTO(Date dob, String phoneNumber, String address, String firstName, String lastName) {
        this.dateOfBirth = dob;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.firstName = firstName;
        this.lastName = lastName; 
    }

    // Listters and Getters
    public void setDateOfBirth(Date dob) {
        this.dateOfBirth = dob;
    }

    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setEmail(String email) { // Might have to remove
        this.email = email; 
    }

    public String getEmail() { // Might have to remove
        return this.email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return this.address;
    }

    public void setPostings(List<Posting> postings) {
		this.postings = postings;
    }
    
    public List<Posting> getPostings() {
        return this.postings;
    }

    public void setComments(List<Comment> comments) {
		this.comments = comments;
    }
    
    public List<Comment> getComments() {
        return this.comments;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return this.firstName; 
    }

    public void setLastName(String lastName) {
        this.lastName = lastName; 
    }

    public String getLastName() {
        return this.lastName; 
    }

    public void setDonations(List<Donation> donations) {
		this.donations = donations;
    }
    
    public List<Donation> getDonations() {
		return this.donations;
    }
    
    public void setMessages(List<Message> messages) {
		this.messages = messages;
    }
    
    public List<Message> getMessages() {
		return this.messages;
    }
    
    public void setApplications(List<Application> applications) {
		this.applications = applications;
    }
    
    public List<Application> getApplications() {
		return this.applications;
	}

}
