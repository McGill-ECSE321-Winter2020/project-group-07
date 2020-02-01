import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Comment{
   private Profile profile;
   
   @ManyToOne(optional=false)
   public Profile getProfile() {
      return this.profile;
   }
   
   public void setProfile(Profile profile) {
      this.profile = profile;
   }
   
   private Posting posting;
   
   @ManyToOne(optional=false)
   public Posting getPosting() {
      return this.posting;
   }
   
   public void setPosting(Posting posting) {
      this.posting = posting;
   }
   
   private String content;

public void setContent(String value) {
    this.content = value;
}
public String getContent() {
    return this.content;
}
private Date date;

public void setDate(Date value) {
    this.date = value;
}
public Date getDate() {
    return this.date;
}
}
