import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

@Entity
public class Posting{
   private Pet pet;
   
   @OneToOne(mappedBy="posting" , optional=false)
   public Pet getPet() {
      return this.pet;
   }
   
   public void setPet(Pet pet) {
      this.pet = pet;
   }
   
   private Application application;
   
   @ManyToOne(optional=false)
   public Application getApplication() {
      return this.application;
   }
   
   public void setApplication(Application application) {
      this.application = application;
   }
   
   private Profile profile;
   
   @ManyToOne(optional=false)
   public Profile getProfile() {
      return this.profile;
   }
   
   public void setProfile(Profile profile) {
      this.profile = profile;
   }
   
   private Set<Comment> comments;
   
   @OneToMany(mappedBy="posting" , cascade={CascadeType.ALL})
   public Set<Comment> getComments() {
      return this.comments;
   }
   
   public void setComments(Set<Comment> commentss) {
      this.comments = commentss;
   }
   
   private Date date;

public void setDate(Date value) {
    this.date = value;
}
public Date getDate() {
    return this.date;
}
private String description;

public void setDescription(String value) {
    this.description = value;
}
public String getDescription() {
    return this.description;
}
}
