import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Message{
   private Admin admin;
   
   @ManyToOne(optional=false)
   public Admin getAdmin() {
      return this.admin;
   }
   
   public void setAdmin(Admin admin) {
      this.admin = admin;
   }
   
   private Client client;
   
   @ManyToOne(optional=false)
   public Client getClient() {
      return this.client;
   }
   
   public void setClient(Client client) {
      this.client = client;
   }
   
   private String content;

public void setContent(String value) {
    this.content = value;
}
public String getContent() {
    return this.content;
}
private boolean date;

public void setDate(boolean value) {
    this.date = value;
}
public boolean isDate() {
    return this.date;
}
}
