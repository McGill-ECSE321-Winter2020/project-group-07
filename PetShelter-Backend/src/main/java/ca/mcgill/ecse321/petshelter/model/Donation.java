package ca.mcgill.ecse321.petshelter.model;
import javax.persistence.Id;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Donation{
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
	private Integer amount;

	public void setAmount(Integer value) {
		this.amount = value;
	}
	public Integer getAmount() {
		return this.amount;
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
