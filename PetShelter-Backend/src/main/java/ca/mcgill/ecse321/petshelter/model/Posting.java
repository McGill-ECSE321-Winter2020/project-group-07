package ca.mcgill.ecse321.petshelter.model;
import javax.persistence.Id;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

@Entity
public class Posting{
	private Date date;

	public void setDate(Date value) {
		this.date = value;
	}
	public Date getDate() {
		return this.date;
	}
	private Date petDateOfBirth;

	public void setPetDateOfBirth(Date value) {
		this.petDateOfBirth = value;
	}
	public Date getPetDateOfBirth() {
		return this.petDateOfBirth;
	}
	private Integer id;

	public void setId(Integer value) {
		this.id = value;
	}
	@Id
	public Integer getId() {
		return this.id;
	}
	private String description;

	public void setDescription(String value) {
		this.description = value;
	}
	public String getDescription() {
		return this.description;
	}
	private String picture;

	public void setPicture(String value) {
		this.picture = value;
	}
	public String getPicture() {
		return this.picture;
	}
	private Profile profile;

	@ManyToOne(optional=false)
	public Profile getProfile() {
		return this.profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	private Set<Comment> comment;

	@OneToMany(mappedBy="posting" , cascade={CascadeType.ALL})
	public Set<Comment> getComment() {
		return this.comment;
	}

	public void setComment(Set<Comment> commentss) {
		this.comment = commentss;
	}

	private Set<Application> application;

	@OneToMany(mappedBy="posting" )
	public Set<Application> getApplication() {
		return this.application;
	}

	public void setApplication(Set<Application> applicationss) {
		this.application = applicationss;
	}

	private String petName;

	public void setPetName(String value) {
		this.petName = value;
	}
	public String getPetName() {
		return this.petName;
	}
	private String petBreed;

	public void setPetBreed(String value) {
		this.petBreed = value;
	}
	public String getPetBreed() {
		return this.petBreed;
	}
}
