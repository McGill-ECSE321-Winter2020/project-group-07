package ca.mcgill.ecse321.petshelter.dto;

import java.sql.Date;

import ca.mcgill.ecse321.petshelter.model.Client;

/**
 * This class is for the data transfer object for donation,
 * implements all the getters and setters to create and use DTOs.
 *
 */
public class DonationDTO {
	private Date date;
	private Integer amount;
	private Integer id;
	private ClientDTO client;
	


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

	public ClientDTO getClient() {
		return client;
	}

	public void setClient(ClientDTO client) {
		this.client = client;
	}

	
}
