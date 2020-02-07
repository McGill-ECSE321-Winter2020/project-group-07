package ca.mcgill.ecse321.petshelter.model;

import javax.persistence.Entity;

import java.util.ArrayList;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class Client extends Profile{
	private String firstName;

	private void setFirstName(String value) {
		this.firstName = value;
	}
	private String getFirstName() {
		return this.firstName;
	}
	private String lastName;

	private void setLastName(String value) {
		this.lastName = value;
	}
	private String getLastName() {
		return this.lastName;
	}
	private ArrayList<Donation> donations;

	@OneToMany(mappedBy="client" )
	public ArrayList<Donation> getDonations() {
		return this.donations;
	}

	public void setDonations(ArrayList<Donation> donationss) {
		this.donations = donationss;
	}

	private Set<Message> messages;

	@OneToMany(mappedBy="client" )
	public Set<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(Set<Message> messagess) {
		this.messages = messagess;
	}

	private ArrayList<Application> applications;

	@OneToMany(mappedBy="client" )
	public ArrayList<Application> getApplications() {
		return this.applications;
	}

	public void setApplications(ArrayList<Application> applicationss) {
		this.applications = applicationss;
	}

}
