package ca.mcgill.ecse321.petshelter.model;
import javax.persistence.Id;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

@Entity
public class Posting{
private Date date;

private void setDate(Date value) {
this.date = value;
}
private Date getDate() {
return this.date;
}
private Date petDateOfBirth;

private void setPetDateOfBirth(Date value) {
this.petDateOfBirth = value;
}
private Date getPetDateOfBirth() {
return this.petDateOfBirth;
}
private int id;

private void setId(int value) {
this.id = value;
}
@Id
private int getId() {
return this.id;
}
   private String description;

private void setDescription(String value) {
    this.description = value;
}
private String getDescription() {
    return this.description;
}
private String picture;

private void setPicture(String value) {
    this.picture = value;
}
private String getPicture() {
    return this.picture;
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

private Set<Application> applications;

@OneToMany(mappedBy="posting" )
public Set<Application> getApplications() {
   return this.applications;
}

public void setApplications(Set<Application> applicationss) {
   this.applications = applicationss;
}

private String petName;

private void setPetName(String value) {
    this.petName = value;
}
private String getPetName() {
    return this.petName;
}
private String petBreed;

private void setPetBreed(String value) {
    this.petBreed = value;
}
private String getPetBreed() {
    return this.petBreed;
}
}
