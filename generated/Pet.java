import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Pet{
   private Posting posting;
   
   @OneToOne(optional=false)
   public Posting getPosting() {
      return this.posting;
   }
   
   public void setPosting(Posting posting) {
      this.posting = posting;
   }
   
   private String name;

public void setName(String value) {
    this.name = value;
}
public String getName() {
    return this.name;
}
private Integer age;

public void setAge(Integer value) {
    this.age = value;
}
public Integer getAge() {
    return this.age;
}
private String breed;

public void setBreed(String value) {
    this.breed = value;
}
public String getBreed() {
    return this.breed;
}
}
