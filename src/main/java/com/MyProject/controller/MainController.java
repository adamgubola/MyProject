package com.MyProject.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.MyProject.entity.User;
import com.MyProject.entity.Wish;
import com.MyProject.service.EmailService;
import com.MyProject.service.UserService;
import com.MyProject.service.WishService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;




@Controller
public class MainController {
	
	private final Logger log =LoggerFactory.getLogger(this.getClass());
	
	private EmailService emailService;
	@Autowired
	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}
	
	private UserService userService;
	@Autowired
	public void setUserService(UserService userService) {
		this.userService=userService;
	}
	
	private WishService wishService;
	@Autowired
	public void setWishService(WishService wishService) {
			this.wishService = wishService;
	}
	
	@GetMapping("/")
	public String Index() {
		return "index";
	}
	@GetMapping("/index")
	public String Index2() {
		return "index";
	}
	
	@GetMapping("/login")
	public String login() {
		return "auth/login";
	}
	
	@GetMapping("/registration")
	public String registration(Model model) {
		model.addAttribute("user", new User());
		return "registration";
	}
	
	@PostMapping("/reg")
	public String reg(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model) {
		log.info("Új user regisztráció");
		if(bindingResult.hasErrors()) {
			return "registration";
		}
		userService.registerUser(user);
		emailService.sendMessage(user.getFirstName(), user.getLastName(), user.getEmail(), user.getActivation());
		
		user.setActivation("");
		String message=userService.registerUser(user);

		model.addAttribute("message", message);

				
		return "/activate";
	}
	
	@GetMapping("/activate")
	public String activate(Model model) {
		model.addAttribute("user", new User());
		return"activate";
	}
	
	@PostMapping("/activ")
	public String activ(@ModelAttribute User user, Model model) {
		userService.userActivation(user.getActivation());
		String message=	userService.userActivation(user.getActivation());
		model.addAttribute("message", message);


		return "auth/login";
	}
	
	@GetMapping("/wishes")
	public String wishes(Model model,@AuthenticationPrincipal UserDetails userDetails, @Valid @ModelAttribute Wish wish,
						 BindingResult bindingResult) {
		
		User autetichatedUser= userService.findByEmail(userDetails.getUsername());
		List<Wish> haveWish= wishService.findAllByUserId(autetichatedUser.getId());
		
		if(haveWish==null || haveWish.isEmpty()) {
			model.addAttribute("wish", new Wish());
		
			if(bindingResult.hasErrors()) {
				return "thereNoWish";}

			return "thereNoWish";
		}
		model.addAttribute("wish", new Wish());
		model.addAttribute("wishes", wishService.findAllByUserId(autetichatedUser.getId()));
		return "wishes";
	}

	@PostMapping("/addWish")
		public String addWish(Model model,@Valid @ModelAttribute Wish wish, BindingResult bindingResult,  @AuthenticationPrincipal UserDetails userDetails ){
			if(bindingResult.hasErrors()) {
			return "wishes";
			}
			wishService.addWish(wish, userDetails);
			User autetichatedUser= userService.findByEmail(userDetails.getUsername());
			model.addAttribute("wishes", wishService.findAllByUserId(autetichatedUser.getId()));
			model.addAttribute("wish", new Wish());
			String message=	wishService.addWish(wish, userDetails);
			model.addAttribute("message", message);

		
			return "wishes";
		}
	
	@PostMapping("/delWish/{id}")
		public String delWish(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
		
		wishService.deleteById(id);
		User autetichatedUser= userService.findByEmail(userDetails.getUsername());
		model.addAttribute("wishes", wishService.findAllByUserId(autetichatedUser.getId()));
		model.addAttribute("wish", new Wish());
		String message=wishService.deleteById(id);
		model.addAttribute("message", message);
		
		return "wishes";
	}
	
	@PostMapping("/updateWish/{id}")
	public String updateWish(@PathVariable Long id, Model model,@Valid @ModelAttribute Wish wish, BindingResult bindingResult, 
							@AuthenticationPrincipal UserDetails userDetails) {
		if(bindingResult.hasErrors()) {
			return "wishes";
			}
		wishService.updateWish(id, wish);
		
		User autetichatedUser= userService.findByEmail(userDetails.getUsername());
		model.addAttribute("wishes", wishService.findAllByUserId(autetichatedUser.getId()));
		model.addAttribute("wish", new Wish());
		String message = wishService.updateWish(id, wish);
		model.addAttribute("message", message);
		
		return "wishes";
	}
	
	@PostMapping("/searchWish")
	public String searchWish(@RequestParam (name="query", required=false, defaultValue="") String query, Model model, @Valid @ModelAttribute Wish wish,
							BindingResult bindingResult, @AuthenticationPrincipal UserDetails userDetails) {
		if(bindingResult.hasErrors()) {
			return "wishes";
			}
		User autetichatedUser= userService.findByEmail(userDetails.getUsername());
		Long currentUserId= autetichatedUser.getId();
		List<Wish>searchedWish = wishService.searchByQuery(currentUserId, query);
		
		if(searchedWish==null || searchedWish.isEmpty()) {
			model.addAttribute("wishes", wishService.findAllByUserId(autetichatedUser.getId()));
			model.addAttribute("wish", new Wish());

			model.addAttribute("message", "A keresés nem vezetett eredményre!");
			return "wishes";
		}else {
			model.addAttribute("wishes",searchedWish);
			model.addAttribute("wish", new Wish());

			model.addAttribute("message", "A keresés eredménye: ");
			return "wishes";
		}
	}
	
	


	
	}
	
	


