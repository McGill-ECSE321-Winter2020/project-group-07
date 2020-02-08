package ca.mcgill.ecse321.petshelter.model;
import javax.persistence.Id;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Application{
	private HomeType homeType;

	public void setHomeType(HomeType value) {
		this.homeType = value;
	}
	public HomeType getHomeType() {
		return this.homeType;
	}
	private IncomeRange incomeRange;

	public void setIncomeRange(IncomeRange value) {
		this.incomeRange = value;
	}
	public IncomeRange getIncomeRange() {
		return this.incomeRange;
	}
	private ApplicationStatus status;

	public void setStatus(ApplicationStatus value) {
		this.status = value;
	}
	public ApplicationStatus getStatus() {
		return this.status;
	}
	private Integer id;

	public void setId(Integer value) {
		this.id = value;
	}
	@Id
	public Integer getId() {
		return this.id;
	}
	private Integer numberOfResidents;

	public void setNumberOfResidents(Integer value) {
		this.numberOfResidents = value;
	}
	public Integer getNumberOfResidents() {
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
