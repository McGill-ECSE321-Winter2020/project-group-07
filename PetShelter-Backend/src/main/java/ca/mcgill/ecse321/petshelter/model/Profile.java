package ca.mcgill.ecse321.petshelter.model;
import java.sql.Date;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public abstract class Profile{
private Date dateOfBirth;

private void setDateOfBirth(Date value) {
this.dateOfBirth = value;
}
private Date getDateOfBirth() {
return this.dateOfBirth;
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
   private Set<Posting> posting;
   
   @OneToMany(mappedBy="profile" )
   public Set<Posting> getPosting() {
      return this.posting;
   }
   
   public void setPosting(Set<Posting> postingss) {
      this.posting = postingss;
   }
   
   private Set<Comment> comment;
   
   @OneToMany(mappedBy="profile" )
   public Set<Comment> getComment() {
      return this.comment;
   }
   
   public void setComment(Set<Comment> commentss) {
      this.comment = commentss;
   }
   
   }
