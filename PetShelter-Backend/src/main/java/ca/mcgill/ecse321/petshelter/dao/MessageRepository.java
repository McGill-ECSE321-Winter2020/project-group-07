package ca.mcgill.ecse321.petshelter.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.petshelter.model.Message;

public interface MessageRepository extends CrudRepository<Message, String>{

	Message findMessageByName(String name);

}