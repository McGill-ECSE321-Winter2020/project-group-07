package ca.mcgill.ecse321.petshelter.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.petshelter.model.Application;

public interface ApplicationRepository extends CrudRepository<Application, String>{

	Application findApplicationByName(String name);

}