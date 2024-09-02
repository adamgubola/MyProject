package com.MyProject.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.MyProject.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	User findByEmail(String email);
	
	User findByActivation(String activation);

}
