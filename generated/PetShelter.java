import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

@Entity
public class PetShelter{
   private Set<Profile> profile;
   
   @OneToMany(mappedBy="petShelter" , cascade={CascadeType.ALL})
   public Set<Profile> getProfile() {
      return this.profile;
   }
   
   public void setProfile(Set<Profile> profiles) {
      this.profile = profiles;
   }
   
   }
