import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class Client extends Profile{
   private Set<Message> message;
   
   @OneToMany(mappedBy="client" )
   public Set<Message> getMessage() {
      return this.message;
   }
   
   public void setMessage(Set<Message> messages) {
      this.message = messages;
   }
   
   private Set<Donation> donation;
   
   @OneToMany(mappedBy="client" )
   public Set<Donation> getDonation() {
      return this.donation;
   }
   
   public void setDonation(Set<Donation> donations) {
      this.donation = donations;
   }
   
   private Set<Application> applications;
   
   @OneToMany(mappedBy="applicant" )
   public Set<Application> getApplications() {
      return this.applications;
   }
   
   public void setApplications(Set<Application> applicationss) {
      this.applications = applicationss;
   }
   
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
private Integer age;

public void setAge(Integer value) {
    this.age = value;
}
public Integer getAge() {
    return this.age;
}
}
