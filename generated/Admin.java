import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class Admin extends Profile{
   private Set<Message> message;
   
   @OneToMany(mappedBy="admin" )
   public Set<Message> getMessage() {
      return this.message;
   }
   
   public void setMessage(Set<Message> messages) {
      this.message = messages;
   }
   
   }
