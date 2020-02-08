package ca.mcgill.ecse321.petshelter.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.petshelter.model.Admin;

public interface AdminRepository extends CrudRepository<Admin, String>{

	Admin findAdminByName(String name);

}