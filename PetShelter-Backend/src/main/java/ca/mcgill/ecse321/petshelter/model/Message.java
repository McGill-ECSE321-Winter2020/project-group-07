package ca.mcgill.ecse321.petshelter.model;
import javax.persistence.Id;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Message{
	private Date date;

	public void setDate(Date value) {
		this.date = value;
	}
	public Date getDate() {
		return this.date;
	}
	private Integer id;

	public void setId(Integer value) {
		this.id = value;
	}
	@Id
	public Integer getId() {
		return this.id;
	}
	private String content;

	public void setContent(String value) {
		this.content = value;
	}
	public String getContent() {
		return this.content;
	}
	private Admin admin;

	@ManyToOne(optional=false)
	public Admin getAdmin() {
		return this.admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	private Client client;

	@ManyToOne(optional=false)
	public Client getClient() {
		return this.client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

}
