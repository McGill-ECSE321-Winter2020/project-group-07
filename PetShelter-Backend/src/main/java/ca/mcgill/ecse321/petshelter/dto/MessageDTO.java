package ca.mcgill.ecse321.petshelter.dto;
import java.sql.Date;

import ca.mcgill.ecse321.petshelter.model.Admin;
import ca.mcgill.ecse321.petshelter.model.Client;

public class MessageDTO {
	
	private Date date;
	private String content;
	private ClientDTO client;
	private Admin admin;
	private Integer id;
	
	public MessageDTO(Date d, String cont, ClientDTO c, Admin a, Integer Id) {
		this.admin =a;
		this.client = c;
		this.date= d;
		this.id = Id;
	}

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
