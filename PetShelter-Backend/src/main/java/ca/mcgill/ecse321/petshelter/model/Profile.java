package ca.mcgill.ecse321.petshelter.model;
import java.sql.Date;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public abstract class Profile{
	public Date dateOfBirth;

	public void setDateOfBirth(Date value) {
		this.dateOfBirth = value;
	}
	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}
	private String email;

	public void setEmail(String value) {
		this.email = value;
	}
	public String getEmail() {
		return this.email;
	}
	private String password;

	public void setPassword(String value) {
		this.password = value;
	}
	public String getPassword() {
		return this.password;
	}
	private Integer phoneNumber;

	public void setPhoneNumber(Integer value) {
		this.phoneNumber = value;
	}
	public Integer getPhoneNumber() {
		return this.phoneNumber;
	}
	private String address;

	public void setAddress(String value) {
		this.address = value;
	}
	public String getAddress() {
		return this.address;
	}
	private Set<Posting> postings;

	@OneToMany(mappedBy="profile" )
	public Set<Posting> getPostings() {
		return this.postings;
	}

	public void setPostings(Set<Posting> postings) {
		this.postings = postings;
	}

	private Set<Comment> comments;

	@OneToMany(mappedBy="profile" )
	public Set<Comment> getComments() {
		return this.comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

}
