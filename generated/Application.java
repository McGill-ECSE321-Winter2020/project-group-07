import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;

@Entity
public class Application{
   private Set<Posting> posting;
   
   @OneToMany(mappedBy="application" )
   public Set<Posting> getPosting() {
      return this.posting;
   }
   
   public void setPosting(Set<Posting> postings) {
      this.posting = postings;
   }
   
   private Client applicant;
   
   @ManyToOne(optional=false)
   public Client getApplicant() {
      return this.applicant;
   }
   
   public void setApplicant(Client applicant) {
      this.applicant = applicant;
   }
   
   private Status status;

public void setStatus(Status value) {
    this.status = value;
}
public Status getStatus() {
    return this.status;
}
private Integer numberOfResidents;

public void setNumberOfResidents(Integer value) {
    this.numberOfResidents = value;
}
public Integer getNumberOfResidents() {
    return this.numberOfResidents;
}
private HomeType homeType;

public void setHomeType(HomeType value) {
    this.homeType = value;
}
public HomeType getHomeType() {
    return this.homeType;
}
}
