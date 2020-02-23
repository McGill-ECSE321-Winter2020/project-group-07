package ca.mcgill.ecse321.petshelter;

public class ErrorMessages { 

    // Client
    public static String accountExists = "Email has already been taken.";
    public static String accountDoesNotExist = "Account does not exist.";
    public static String invalidDOB = "Invalid date of birth.";
    public static String invalidEmail = "Invalid email address.";
    public static String invalidPassword = "Invalid password.";
    public static String invalidPhoneNumber = "Invalid phone number.";
    public static String invalidAddress = "Invalid street address.";
    public static String invalidFirstName = "Invalid first name.";
    public static String invalidLastName = "Invalid last name."; 
    public static String under18 = "Must be at least 18 years of age to register.";
    
    // Comment
    public static String invalidPostingComment = "Invalid posting.";
    public static String invalidProfileComment = "Invalid profile.";
    public static String invalidContentComment = "Cannot post an empty comment.";
    public static String invalidDateComment = "Cannot post a comment that defies the almighty law of physiscs that is Time.";
    
    //Application 
    public static String selfApplication = "Cannot apply to your own posting.";
    
    //Donations
    public static String negAmount = "Amount needs to be whole and positive number!";
    public static String incorrectCharacter = "Amount cannot be a letter or a special character!";
    public static String DateDonation = "No date for donation.";
    
    //Messages
    public static String NoContent = "You need to write a message before sending it.";
    public static String tooLong = "Your message is too long.";
    public static String dateMessage = "No date for message.";
    public static String MessAlreadyExists = "The message you are trying to send is identical to a previous message already sent.";
}
