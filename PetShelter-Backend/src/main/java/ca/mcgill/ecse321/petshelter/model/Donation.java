package ca.mcgill.ecse321.petshelter.model;
import javax.persistence.Id;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Donation{
private Date date;

private void setDate(Date value) {
this.date = value;
}
private Date getDate() {
return this.date;
}
private int id;

private void setId(int value) {
this.id = value;
}
@Id
private int getId() {
return this.id;
}
   private Integer amount;

private void setAmount(Integer value) {
    this.amount = value;
}
private Integer getAmount() {
    return this.amount;
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
