package ca.mcgill.ecse321.petshelter.dto;

import java.sql.Date;
import java.util.Set;

import ca.mcgill.ecse321.petshelter.model.*;

public class ProfileDTO {

    // Profile attributes
    private Date dateOfBirth; 
	private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private Set<PostingDTO> postings; 
    private Set<CommentDTO> comments;
    private boolean isLoggedIn;
    
    
    //Constructor
	public ProfileDTO(Date dateOfBirth, String email, String password, String phoneNumber, String address,
			Set<PostingDTO> postings, Set<CommentDTO> comments, boolean isLoggedIn) {
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.postings = postings;
		this.comments = comments;
		this.isLoggedIn = isLoggedIn;
	}
    
	//Getters and Setters
    public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Set<PostingDTO> getPostings() {
		return postings;
	}
	public void setPostings(Set<PostingDTO> postings) {
		this.postings = postings;
	}
	public Set<CommentDTO> getComments() {
		return comments;
	}
	public void setComments(Set<CommentDTO> comments) {
		this.comments = comments;
	}
	public boolean isLoggedIn() {
		return isLoggedIn;
	}
	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}
}
