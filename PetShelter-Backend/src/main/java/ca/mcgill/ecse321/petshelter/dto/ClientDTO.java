package ca.mcgill.ecse321.petshelter.dto;

import ca.mcgill.ecse321.petshelter.model.*;

import java.sql.Date;
import java.util.Set; 
import java.util.List; 

public class ClientDTO {

    // Profile attributes
    private Date dateOfBirth; 
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private List<PostingDTO> postings; 
    private List<CommentDTO> comments; 
    private boolean isLoggedIn;

    // Client attributes
    private String firstName; 
    private String lastName; 
    private List<DonationDTO> donations;
    private List<MessageDTO> messages;
    private List<ApplicationDTO> applications; 

    // Can add more constructors later based on functionality needed
    public ClientDTO() {
    }

    // Creating an account
    public ClientDTO(Date dob, String email, String phoneNumber, String address, List<PostingDTO> postings, 
                     List<CommentDTO> comments, boolean isLoggedIn, String firstName, String lastName, 
                     List<DonationDTO> donations, List<MessageDTO> messages, List<ApplicationDTO> applications) {
        this.dateOfBirth = dob;
        this.email = email; 
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.postings = postings;
        this.comments = comments;
        this.isLoggedIn = isLoggedIn;
        this.firstName = firstName;
        this.lastName = lastName; 
        this.donations = donations;
        this.messages = messages;
        this.applications = applications; 
    }

    // Updating information
    public ClientDTO(Date dob, String password, String phoneNumber, String address, 
                     boolean isLoggedIn, String firstName, String lastName) {
        this.dateOfBirth = dob;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.isLoggedIn = isLoggedIn;
        this.firstName = firstName;
        this.lastName = lastName; 
    }

    // Viewing others profile
    public ClientDTO(Date dob, String email, boolean isLoggedIn, 
                     String firstName, String lastName, List<PostingDTO> postings) {
        this.dateOfBirth = dob; 
        this.email = email;
        this.isLoggedIn = isLoggedIn;
        this.firstName = firstName; 
        this.lastName = lastName; 
        this.postings = postings;
    }

    // The person who posted your accepted application
    public ClientDTO(Date dob, String phoneNumber, String address, 
                     boolean isLoggedIn, String firstName, String lastName) {
        this.dateOfBirth = dob;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.isLoggedIn = isLoggedIn;
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

    public void setPostings(List<PostingDTO> postings) {
		this.postings = postings;
    }
    
    public List<PostingDTO> getPostings() {
        return this.postings;
    }

    public void setComments(List<CommentDTO> comments) {
		this.comments = comments;
    }
    
    public List<CommentDTO> getComments() {
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

    public void setDonations(List<DonationDTO> donations) {
		this.donations = donations;
    }
    
    public List<DonationDTO> getDonations() {
		return this.donations;
    }
    
    public void setMessages(List<MessageDTO> messages) {
		this.messages = messages;
    }
    
    public List<MessageDTO> getMessages() {
		return this.messages;
    }
    
    public void setApplications(List<ApplicationDTO> applications) {
		this.applications = applications;
    }
    
    public List<ApplicationDTO> getApplications() {
		return this.applications;
	}

}
