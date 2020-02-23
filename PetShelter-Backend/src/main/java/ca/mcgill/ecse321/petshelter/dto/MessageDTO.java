package ca.mcgill.ecse321.petshelter.dto;
import java.sql.Date;

import ca.mcgill.ecse321.petshelter.model.Admin;
import ca.mcgill.ecse321.petshelter.model.Client;


/**
 * This class is the data transfer object class for messages, 
 * implements all the getters and setters for messages.
 * @author Alexander
 *
 */
public class MessageDTO {
	
	private Date date;
	private String content;
	private ClientDTO client;
	private Admin admin;
	private Integer id;
	

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ClientDTO getClient() {
		return client;
	}

	public void setClient(ClientDTO client) {
		this.client = client;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
