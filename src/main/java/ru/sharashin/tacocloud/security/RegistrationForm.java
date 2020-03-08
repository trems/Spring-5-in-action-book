package ru.sharashin.tacocloud.security;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.sharashin.tacocloud.domain.User;

@Data
public class RegistrationForm {

	private String username;
	private	String password;
	private	String fullname;
	private	String street;
	private	String city;
	private	String state;
	private	String zip;
	private	String phoneNumber;

	public User toUser(PasswordEncoder passwordEncoder) {
		return new User(username, passwordEncoder.encode(password), fullname, phoneNumber, street, city, state, zip);
	}

}
