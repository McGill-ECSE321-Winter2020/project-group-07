package ca.mcgill.ecse321.petshelter.model;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class Client extends Profile{
	private String firstName;

	public void setFirstName(String value) {
		this.firstName = value;
	}
	public String getFirstName() {
		return this.firstName;
	}
	private String lastName;

	public void setLastName(String value) {
		this.lastName = value;
	}
	public String getLastName() {
		return this.lastName;
	}
	
	@ElementCollection(targetClass=Donation.class)
	private Set<Donation> donations;

	@OneToMany(mappedBy="client" )
	public Set<Donation> getDonations() {
		return this.donations;
	}

	public void setDonations(Set<Donation> donations) {
		this.donations = donations;
	}

	@ElementCollection(targetClass=Message.class)
	private Set<Message> messages;

	@OneToMany(mappedBy="client" )
	public Set<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

	@ElementCollection(targetClass=Application.class)
	private Set<Application> applications;

	@OneToMany(mappedBy="client" )
	public Set<Application> getApplications() {
		return this.applications;
	}

	public void setApplications(Set<Application> applications) {
		this.applications = applications;
	}

}
