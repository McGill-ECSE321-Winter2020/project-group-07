package ca.mcgill.ecse321.petshelter.dto;

import java.sql.Date;

import ca.mcgill.ecse321.petshelter.model.Client;


public class DonationDTO {
	private Date date;
	private Integer amount;
	private Integer id;
	private Client client;
	
	public DonationDTO(Date d, Integer am, Integer Id, Client cl) {
		this.amount = am;
		this.client = cl;
		this.id = Id;
		this.date = d;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	
}
