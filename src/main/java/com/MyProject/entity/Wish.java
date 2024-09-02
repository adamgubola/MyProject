package com.MyProject.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "wishes")
public class Wish {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "wish_id")
	private Long id;
	
	private String place;
	
	private String what;
	
	private String description;
	@Pattern(regexp = "^\\d{1,18}$", message = "Az értéknek egész számak kell lennie 1 és 18 karakter között")
	private String price;
	@Pattern(regexp = "^\\d{1,18}$", message = "Az értéknek egész számak kell lennie 1 és 18 karakter között")
	private String savedMoney;
	
	@ManyToOne
	private User user;
	
	public Wish() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getWhat() {
		return what;
	}

	public void setWhat(String what) {
		this.what = what;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getSavedMoney() {
		return savedMoney;
	}

	public void setSavedMoney(String savedMoney) {
		this.savedMoney = savedMoney;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

}
