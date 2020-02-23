package ca.mcgill.ecse321.petshelter.dto;

import java.sql.Date;
import java.util.List;

import ca.mcgill.ecse321.petshelter.model.*;

public class ProfileDTO {

    // Profile attributes
    private Date dateOfBirth; 
	private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private List<PostingDTO> postings; 
    private List<CommentDTO> comments;
    private boolean isLoggedIn;
    
    
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
	public List<PostingDTO> getPostings() {
		return postings;
	}
	public void setPostings(List<PostingDTO> postings) {
		this.postings = postings;
	}
	public List<CommentDTO> getComments() {
		return comments;
	}
	public void setComments(List<CommentDTO> comments) {
		this.comments = comments;
	}
	public boolean isLoggedIn() {
		return isLoggedIn;
	}
	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}
}
