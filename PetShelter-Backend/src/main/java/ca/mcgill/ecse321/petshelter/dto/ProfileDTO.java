package ca.mcgill.ecse321.petshelter.dto;

import java.sql.Date;
import java.util.Set;

import ca.mcgill.ecse321.petshelter.model.*;

public class ProfileDTO {

    // Profile attributes
    private Date dateOfBirth; 
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private Set<PostingDTO> postings; 
    private Set<CommentDTO> comments;
    private boolean isLoggedIn;
}
