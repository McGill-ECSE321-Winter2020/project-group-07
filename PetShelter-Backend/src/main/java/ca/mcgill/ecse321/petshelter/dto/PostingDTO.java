package ca.mcgill.ecse321.petshelter.dto;

import ca.mcgill.ecse321.petshelter.model.*;

import java.sql.Date;
import java.util.List;

public class PostingDTO {
	
	//Posting Attributes
	private Integer Id;
	private Date date;
	private String picture;
	private String description;
	private String petName;
	private String petBreed;
	private Date petDateOfBirth;
	private ProfileDTO profile;
	private List <ApplicationDTO> applications;
	private List <CommentDTO> comments;
	
	//Constructor
	public PostingDTO(Integer id, Date date, String picture, String description, 
			String petName, String petBreed,Date petDateOfBirth, ProfileDTO profile, 
			List<ApplicationDTO> applications, List<CommentDTO> comments) {
		
		Id = id;
		this.date = date;
		this.picture = picture;
		this.description = description;
		this.petName = petName;
		this.petBreed = petBreed;
		this.petDateOfBirth = petDateOfBirth;
		this.profile = profile;
		this.applications = applications;
		this.comments = comments;
	}
	
	//Setters and Getters
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPetName() {
		return petName;
	}
	public void setPetName(String petName) {
		this.petName = petName;
	}
	public String getPetBreed() {
		return petBreed;
	}
	public void setPetBreed(String petBreed) {
		this.petBreed = petBreed;
	}
	public Date getPetDateOfBirth() {
		return petDateOfBirth;
	}
	public void setPetDateOfBirth(Date petDateOfBirth) {
		this.petDateOfBirth = petDateOfBirth;
	}
	public ProfileDTO getProfile() {
		return profile;
	}

	public void setProfile(ProfileDTO profile) {
		this.profile = profile;
	}

	public List<ApplicationDTO> getApplications() {
		return applications;
	}

	public void setApplications(List<ApplicationDTO> applications) {
		this.applications = applications;
	}

	public List<CommentDTO> getComments() {
		return comments;
	}

	public void setComments(List<CommentDTO> comments) {
		this.comments = comments;
	}
}
