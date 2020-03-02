package ca.mcgill.ecse321.petshelter.model;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import java.util.HashSet;
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
	
	public Set<Donation> addDonation(Donation donation){
		if (this.donations == null) {
			this.donations = new HashSet<>();
		}
		this.donations.add(donation);
		return this.donations;
	}
	
	public Set<Donation> removeDonation(Donation donation){
		if (this.donations == null) {
			return null;
		}
		this.donations.remove(donation);
		return this.donations;
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
	
	public Set<Message> addMessage(Message message){
		if (this.messages == null) {
			this.messages = new HashSet<>();
		}
		this.messages.add(message);
		return this.messages;
	}
	
	public Set<Message> removeMessage(Message message){
		if (this.messages == null) {
			return null;
		}
		this.messages.remove(message);
		return this.messages;
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
	
	public Set<Application> addApplication(Application application){
		if (this.applications == null) {
			this.applications = new HashSet<>();
		}
		this.applications.add(application);
		return this.applications;
	}
	
	public Set<Application> removeApplication(Application application){
		if (this.applications == null) {
			return null;
		}
		this.applications.remove(application);
		return this.applications;
	}

}
