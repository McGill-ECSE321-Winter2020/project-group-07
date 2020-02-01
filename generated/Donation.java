import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Donation{
   private Client client;
   
   @ManyToOne(optional=false)
   public Client getClient() {
      return this.client;
   }
   
   public void setClient(Client client) {
      this.client = client;
   }
   
   private Integer amount;

public void setAmount(Integer value) {
    this.amount = value;
}
public Integer getAmount() {
    return this.amount;
}
private Date date;

public void setDate(Date value) {
    this.date = value;
}
public Date getDate() {
    return this.date;
}
}
