package ca.mcgill.ecse321.petshelter.dto;

import ca.mcgill.ecse321.petshelter.model.*;

import java.sql.Date;
import java.util.Set; 

public class ClientDTO {

    // Profile attributes
    private Date dateOfBirth; 
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private Set<Posting> postings; 
    private Set<Comment> comments; 

    // Client attributes
    private String firstName; 
    private String lastName; 
    private Set<Donation> donations;
    private Set<Message> messages;
    private Set<Application> applications; 

    // Can add more constructors later based on functionality needed
    public ClientDTO() {
    }

    // Updating information
    public ClientDTO(Date dob, String email, String password, String phoneNumber, // May have to remove email
                     String address, String firstName, String lastName) {

        this.dateOfBirth = dob;
        this.email = email; // Might get complicated changing the primary key in DB
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.firstName = firstName;
        this.lastName = lastName; 
    }

    // Setters and Getters
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

    public void setPostings(Set<Posting> postings) {
		this.postings = postings;
    }
    
    public Set<Posting> getPostings() {
        return this.postings;
    }

    public void setComments(Set<Comment> comments) {
		this.comments = comments;
    }
    
    public Set<Comment> getComments() {
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

    public void setDonations(Set<Donation> donations) {
		this.donations = donations;
    }
    
    public Set<Donation> getDonations() {
		return this.donations;
    }
    
    public void setMessages(Set<Message> messages) {
		this.messages = messages;
    }
    
    public Set<Message> getMessages() {
		return this.messages;
    }
    
    public void setApplications(Set<Application> applications) {
		this.applications = applications;
    }
    
    public Set<Application> getApplications() {
		return this.applications;
	}

}
