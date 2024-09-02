
package com.MyProject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.MyProject.entity.Wish;

@Repository
public interface WishRepository extends CrudRepository<Wish, Long> {
	
	List<Wish>findAllByUserId(Long id);

	Wish deleteById(Wish id);
	
	Optional<Wish> findById(Long id);
	
	

	
	

}
