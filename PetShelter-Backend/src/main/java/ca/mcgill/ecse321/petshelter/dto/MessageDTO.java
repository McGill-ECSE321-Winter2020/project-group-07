package ca.mcgill.ecse321.petshelter.dto;
import ca.mcgill.ecse321.petshelter.model.*;
import java.sql.Date;

public class MessageDTO {
	
	private Date date;
	private String content;
	private Client client;
	private Admin admin;
	private Integer id;
	
	public MessageDTO(Date d, String cont, Client c, Admin a, Integer Id) {
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

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
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
