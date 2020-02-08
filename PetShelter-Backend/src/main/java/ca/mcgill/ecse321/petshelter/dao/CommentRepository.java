package ca.mcgill.ecse321.petshelter.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.petshelter.model.Comment;

public interface CommentRepository extends CrudRepository<Comment, String>{

	Comment findCommentByName(String name);

}