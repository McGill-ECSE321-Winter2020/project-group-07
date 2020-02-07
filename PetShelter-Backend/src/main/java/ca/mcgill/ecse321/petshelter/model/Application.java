package ca.mcgill.ecse321.petshelter.model;
import javax.persistence.Id;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Application{
	private HomeType homeType;

	private void setHomeType(HomeType value) {
		this.homeType = value;
	}
	private HomeType getHomeType() {
		return this.homeType;
	}
	private IncomeRange incomeRange;

	private void setIncomeRange(IncomeRange value) {
		this.incomeRange = value;
	}
	private IncomeRange getIncomeRange() {
		return this.incomeRange;
	}
	private ApplicationStatus status;

	private void setStatus(ApplicationStatus value) {
		this.status = value;
	}
	private ApplicationStatus getStatus() {
		return this.status;
	}
	private int id;

	private void setId(int value) {
		this.id = value;
	}
	@Id
	private int getId() {
		return this.id;
	}
	private Integer numberOfResidents;

	private void setNumberOfResidents(Integer value) {
		this.numberOfResidents = value;
	}
	private Integer getNumberOfResidents() {
		return this.numberOfResidents;
	}
	private Posting posting;

	@ManyToOne(optional=false)
	public Posting getPosting() {
		return this.posting;
	}

	public void setPosting(Posting posting) {
		this.posting = posting;
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
