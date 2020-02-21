package ca.mcgill.ecse321.petshelter.dto;

import ca.mcgill.ecse321.petshelter.model.*;

import java.sql.Date;
import java.util.Set; 

public class ClientDTO {

    // Profile attributes
    private Date dateOfBirth; 
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private Set<Posting> postings; 
    private Set<Comment> comments; 

    // Client attributes
    private String firstName; 
    private String lastName; 
    private Set<Donation> donations;
    private Set<Message> messages;
    private Set<Application> applications; 

    // Can add more constructors later based on functionality needed
    public ClientDTO() {
    }

    // Updating information
    public ClientDTO(Date dateOfBirth, String email, String password, 
                    String phoneNumber, String address, String firstName,
                    String lastName) {

    }



}
