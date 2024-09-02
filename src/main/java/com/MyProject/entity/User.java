package com.MyProject.entity;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private Long id;
	
	@Size(min=2, max=20, message = "A felhasználónévnek legalább 4 és maximum 20 karakternek kell lennie")
	private String firstName;
	
	@Column(nullable = false)
	@Size(min=2, max=20, message = "A felhasználónévnek legalább 4 és maximum 20 karakternek kell lennie")
	private String lastName;
	
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{4,}$", message = "A jelszónak legalább 4 karakter hosszúnak kell lennie,"
			+ "tartalmaznia kell legalább 1 kisbetűt, 1 nagybetűt és egy számot")
	private String password;
	
	@Email(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "Nem megfelelő emailcím. Minta: MintaBela@gmail.com")
	@Column(unique = true, nullable = false)
	private String email;
	
	@ManyToMany( fetch = FetchType.EAGER)
	@JoinTable(
			name = "users_roles",
			joinColumns= {@JoinColumn(name="user_id")},
			
			inverseJoinColumns= {@JoinColumn(name="role_id")}
			)
		private Set<Role>roles=new HashSet<Role>();
	
	@OneToMany(mappedBy="user")
	private List<Wish>wishes;
	
	public String activation;
	
	public Boolean enabled;
	
	public User() {
		
	}
	
	public void addRoles(String roleName) {
		if( this.roles==null || this.roles.isEmpty()) {
			this.roles=new HashSet<Role>();
			this.roles.add(new Role(roleName));
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getActivation() {
		return activation;
	}

	public void setActivation(String activation) {
		this.activation = activation;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	

}