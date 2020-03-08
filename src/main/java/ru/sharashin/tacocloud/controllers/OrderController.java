package ru.sharashin.tacocloud.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.spring5.view.ThymeleafView;
import ru.sharashin.tacocloud.data.OrderRepository;
import ru.sharashin.tacocloud.domain.Ingredient;
import ru.sharashin.tacocloud.domain.Order;
import ru.sharashin.tacocloud.domain.User;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {

	private OrderRepository orderRepository;

	@Autowired
	public OrderController(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}



	@GetMapping({"", "/current"})
	public String orderForm(Model model, Order order) {
		List<String> tacosPrettyStrings = transformTacosToPrettyStrings(order);

		model.addAttribute("tacosPrettyStrings", tacosPrettyStrings);
		model.addAttribute(order);
		return "orderForm";
	}

	private List<String> transformTacosToPrettyStrings(Order order) {
		return order.getTacos().stream()
				.map(taco -> {
					StringBuilder sb = new StringBuilder();
					sb.append(taco.getName()).append(" [ ");
					String ingredients = taco.getIngredients().stream().map(Ingredient::getName).collect(Collectors.joining(", "));
					sb.append(ingredients).append(" ]");
					return sb.toString();
				})
				.collect(Collectors.toList());
	}


	@PostMapping
	public String processOrder(Model model, @Valid Order order, Errors errors, SessionStatus sessionStatus, @AuthenticationPrincipal User user) {
		if (errors.hasErrors()) {
			return orderForm(model, order);
		}
		order.setUser(user);
		orderRepository.save(order);
		sessionStatus.setComplete();
		log.info("Order submitted: " + order);
		return "redirect:/";
	}

}
