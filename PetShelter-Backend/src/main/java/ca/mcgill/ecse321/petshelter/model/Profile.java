package ca.mcgill.ecse321.petshelter.model;
import java.sql.Date;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public abstract class Profile{
private Date date;

private void setDate(Date value) {
this.date = value;
}
private Date getDate() {
return this.date;
}
   private String email;

private void setEmail(String value) {
    this.email = value;
}
private String getEmail() {
    return this.email;
}
private String password;

private void setPassword(String value) {
    this.password = value;
}
private String getPassword() {
    return this.password;
}
private Integer phoneNumber;

private void setPhoneNumber(Integer value) {
    this.phoneNumber = value;
}
private Integer getPhoneNumber() {
    return this.phoneNumber;
}
private String address;

private void setAddress(String value) {
    this.address = value;
}
private String getAddress() {
    return this.address;
}
   private Set<Posting> postings;
   
   @OneToMany(mappedBy="profile" )
   public Set<Posting> getPostings() {
      return this.postings;
   }
   
   public void setPostings(Set<Posting> postingss) {
      this.postings = postingss;
   }
   
   private Set<Comment> comments;
   
   @OneToMany(mappedBy="profile" )
   public Set<Comment> getComments() {
      return this.comments;
   }
   
   public void setComments(Set<Comment> commentss) {
      this.comments = commentss;
   }
   
   }
