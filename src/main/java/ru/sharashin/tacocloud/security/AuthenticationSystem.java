package ru.sharashin.tacocloud.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSystem {

	public boolean isLogged() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return null != authentication && !("anonymousUser").equals(authentication.getName());
	}
}
