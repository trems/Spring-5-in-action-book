package ru.sharashin.tacocloud.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

	@RequestMapping("/login")
	public String loginForm() {
		return "login";
	}

	@RequestMapping("/loginError")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		return "login";
	}

}
