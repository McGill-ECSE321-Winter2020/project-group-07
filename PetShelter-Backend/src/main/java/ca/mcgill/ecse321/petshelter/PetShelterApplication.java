package ca.mcgill.ecse321.petshelter;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@SpringBootApplication
public class PetShelterApplication {

  public static void main(String[] args) {
    SpringApplication.run(PetShelterApplication.class, args);
  }

<<<<<<< HEAD
  @RequestMapping("/")
  public String greeting(){
    return "Pet Shelter Application running successfully :)";
  }
=======
//  @RequestMapping("/")
//  public String greeting(){
//    return "Customised";
//  }
>>>>>>> e2d9c08d8af908af2f577416453bb7776bbcb014
  
  

}
