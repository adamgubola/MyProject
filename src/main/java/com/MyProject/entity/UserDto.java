package com.MyProject.entity;

import com.MyProject.validation.EmailMatches;
import com.MyProject.validation.PasswordMatches;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@PasswordMatches
@EmailMatches
public class UserDto {
	
		@Column(nullable = false)
		@Size(min=2, max=20, message = "A felhasználónévnek legalább 4 és maximum 20 karakternek kell lennie")
		private String firstName;
		@Column(nullable = false)
		@Size(min=2, max=20, message = "A felhasználónévnek legalább 4 és maximum 20 karakternek kell lennie")
		private String lastName;
		@Column(nullable = false)
		@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{4,}$", message = "A jelszónak legalább 4 karakter hosszúnak kell lennie,"
				+ "tartalmaznia kell legalább 1 kisbetűt, 1 nagybetűt és egy számot")
		private String password;
		
		@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{4,}$", message = "A jelszónak legalább 4 karakter hosszúnak kell lennie,"
				+ "tartalmaznia kell legalább 1 kisbetűt, 1 nagybetűt és egy számot")
		private String confirmPassword;
		@Column(unique = true, nullable = false)
		@Email(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "Nem megfelelő emailcím. Minta: MintaBela@gmail.com")
		private String email;
		
		@Email(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "Nem megfelelő emailcím. Minta: MintaBela@gmail.com")
		private String confirmEmail;
		
		
		

		public UserDto() {
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

		public String getConfirmPassword() {
			return confirmPassword;
		}

		public void setConfirmPassword(String confirmPassword) {
			this.confirmPassword = confirmPassword;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getConfirmEmail() {
			return confirmEmail;
		}

		public void setConfirmEmail(String confirmEmail) {
			this.confirmEmail = confirmEmail;
		}

		
		
		
}

