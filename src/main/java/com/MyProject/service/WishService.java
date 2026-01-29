package com.MyProject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.MyProject.entity.User;
import com.MyProject.entity.Wish;
import com.MyProject.repository.UserRepository;
import com.MyProject.repository.WishRepository;

@Service
public class WishService {
	
	private WishRepository wishRepository;
	private UserRepository userRepository;
	
	@Autowired
	public WishService(WishRepository wishRepository, UserRepository userRepository) {
		this.wishRepository = wishRepository;
		this.userRepository= userRepository;
	}
	
	public List<Wish>findAllByUserId(Long id){
		return wishRepository.findAllByUserId(id);
	}
	
	public String addWish(Wish wishToAdd, UserDetails userDetails) {
		if(userDetails==null) {
			return "Kérlek jelentkezz be";
		}
		User autentichatesUser= userRepository.findByEmail(userDetails.getUsername());
				wishToAdd.setUser(autentichatesUser);
		wishRepository.save(wishToAdd);
		
		return "A kívánság hozzáava";
	}
	
	public String deleteById(Long id) {
		
		if(id==null) {
			return "A törlés során hiba merült fel! ";
		}
		
		wishRepository.deleteById(id);
		
		return "A kívánság törlve!";
	}
	
	public Optional<Wish> findById(Long id) {
		return wishRepository.findById(id);
	}
	
	public String updateWish(Long id, Wish wishToUpdate) {
		
		Optional<Wish> wishById= findById(id);
		
		if(wishById.isEmpty() || wishById== null) {
			return "A kívánság nem található";
		}
	    Wish existingWish = wishById.get();

	    existingWish.setPlace(wishToUpdate.getPlace());
	    existingWish.setWhat(wishToUpdate.getWhat());
	    existingWish.setDescription(wishToUpdate.getDescription());
	    existingWish.setPrice(wishToUpdate.getPrice());
	    existingWish.setSavedMoney(wishToUpdate.getSavedMoney());

	    wishRepository.save(existingWish);
		
		
		return "A kívánság módosítva";
	}
	
	public List<Wish> searchByQuery(Long id, String query) {
		List<Wish>wishes= wishRepository.findAllByUserId(id);
		List<Wish>matchedWishes= new ArrayList<>();
		
		for(Wish wish: wishes) {
			
			if(wish.getPlace().toLowerCase().contains(query.toLowerCase()) ||
			wish.getWhat().toLowerCase().contains(query.toLowerCase()) ||
			wish.getDescription().toLowerCase().contains(query.toLowerCase()) ||
			wish.getPrice().toLowerCase().contains(query.toLowerCase()) ||
			wish.getSavedMoney().toLowerCase().contains(query.toLowerCase())
			) {
				matchedWishes.add(wish);
			}
		}
		return matchedWishes;
	}

}
