package ca.mcgill.ecse321.petshelter.model;
import javax.persistence.Id;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Comment{
	private Date date;

	public void setDate(Date value) {
		this.date = value;
	}
	public Date getDate() {
		return this.date;
	}
	private int id;

	public void setId(int value) {
		this.id = value;
	}
	@Id
	public int getId() {
		return this.id;
	}
	private String content;

	public void setContent(String value) {
		this.content = value;
	}
	public String getContent() {
		return this.content;
	}
	private Posting posting;

	@ManyToOne(optional=false)
	public Posting getPosting() {
		return this.posting;
	}

	public void setPosting(Posting posting) {
		this.posting = posting;
	}

	private Profile profile;

	@ManyToOne(optional=false)
	public Profile getProfile() {
		return this.profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

}
