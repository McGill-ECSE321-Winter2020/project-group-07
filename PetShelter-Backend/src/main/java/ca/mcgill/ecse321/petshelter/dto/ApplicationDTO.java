package ca.mcgill.ecse321.petshelter.dto;

import ca.mcgill.ecse321.petshelter.model.*;
import ca.mcgill.ecse321.petshelter.dto.ClientDTO;

public class ApplicationDTO {
	private HomeType homeType;
	private IncomeRange incomeRange;
	private ApplicationStatus status;
	private Integer numberOfResidents;
	private Posting posting;
	private ClientDTO clientDTO;
	
	public ClientDTO getClientDTO() {
		return clientDTO;
	}
	public void setClientDTO(ClientDTO clientDTO) {
		this.clientDTO = clientDTO;
	}
	
	public HomeType getHomeType() {
		return homeType;
	}
	public void setHomeType(HomeType homeType) {
		this.homeType = homeType;
	}
	public IncomeRange getIncomeRange() {
		return incomeRange;
	}
	public void setIncomeRange(IncomeRange incomeRange) {
		this.incomeRange = incomeRange;
	}
	public ApplicationStatus getStatus() {
		return status;
	}
	public void setStatus(ApplicationStatus status) {
		this.status = status;
	}
	public Integer getNumberOfResidents() {
		return numberOfResidents;
	}
	public void setNumberOfResidents(Integer numberOfResidents) {
		this.numberOfResidents = numberOfResidents;
	}
	public Posting getPosting() {
		return posting;
	}
	public void setPosting(Posting posting) {
		this.posting = posting;
	}
}
