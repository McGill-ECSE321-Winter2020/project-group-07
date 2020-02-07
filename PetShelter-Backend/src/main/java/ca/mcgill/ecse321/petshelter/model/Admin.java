package ca.mcgill.ecse321.petshelter.model;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class Admin extends Profile{
	
	@ElementCollection(targetClass=Message.class)
	private Set<Message> messages;

	@OneToMany(mappedBy="admin" )
	public Set<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

}
