package com.MyProject.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.MyProject.entity.Role;
import com.MyProject.entity.User;
import com.MyProject.repository.RoleRepository;
import com.MyProject.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
	
	private final String USER_ROLE= "USER";
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
		super();
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email);
	}
	
	@Override
	public User findByActivation(String activation) {
		// TODO Auto-generated method stub
		return userRepository.findByActivation(activation);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("User keresése");
		User user=findByEmail(username);
		if(user==null) {
			throw new UsernameNotFoundException(username);
		}
		System.out.println("Email keresés");
	
		return new UserDetailsImpl(user);
	}

	@Override
	public String registerUser(User userToRegister) {
		
		User userCheck=userRepository.findByEmail(userToRegister.getEmail());
		
		if(userCheck!=null) {
			return "Ez a felhasználói fiók már létezik!";
		}
		
		Role userRole=roleRepository.findByRole(USER_ROLE);
		
		if(userRole!=null) {
			userToRegister.getRoles().add(userRole);
		}else {
			userToRegister.addRoles(USER_ROLE);
		}
		
		String encodedPassword= new BCryptPasswordEncoder().encode(userToRegister.getPassword());
		userToRegister.setPassword(encodedPassword);
		userToRegister.setEnabled(false);
		userToRegister.setActivation(generateKey());
		User u= userRepository.save(userToRegister);
		System.out.println("Az új user regisztrálva");
		
		return "Sikeres regisztráció! Kérlek aktiváld a fiókodat";
	}
	
	public String generateKey() {
		Random random=new Random();
		char[]word= new char[16];
		for(int i=0;i<16;i++) {
			word[i]=(char)('a'+random.nextInt(26));}
		return new String(word);
		}
	
	public String userActivation(String code) {
		
		User user=userRepository.findByActivation(code);
		
		if(user==null) {
			System.out.println("Ez az aktivációs kód nem található, kérlek ellenőrizd a kódot! ");
			
			return "Ez az aktivációs kód nem található, kérlek ellenőrizd a kódot!";
		}
		user.setEnabled(true);
		user.setActivation("");
		userRepository.save(user);
		System.out.println("Az aktiváció sikeres volt!");
		
		return "Az aktiváció sikeres volt!" ;
	}
	}
	
	



	

