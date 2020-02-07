package ca.mcgill.ecse321.petshelter.model;

import javax.persistence.Entity;
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
   private Set<Donation> donation;
   
   @OneToMany(mappedBy="client" )
   public Set<Donation> getDonation() {
      return this.donation;
   }
   
   public void setDonation(Set<Donation> donationss) {
      this.donation = donationss;
   }
   
   private Set<Message> message;
   
   @OneToMany(mappedBy="client" )
   public Set<Message> getMessage() {
      return this.message;
   }
   
   public void setMessage(Set<Message> messagess) {
      this.message = messagess;
   }
   
   private Set<Application> application;
   
   @OneToMany(mappedBy="client" )
   public Set<Application> getApplication() {
      return this.application;
   }
   
   public void setApplication(Set<Application> applicationss) {
      this.application = applicationss;
   }
   
   }
