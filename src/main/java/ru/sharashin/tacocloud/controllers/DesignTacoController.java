package ru.sharashin.tacocloud.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.sharashin.tacocloud.data.IngredientRepository;
import ru.sharashin.tacocloud.data.TacoRepository;
import ru.sharashin.tacocloud.domain.Ingredient;
import ru.sharashin.tacocloud.domain.Ingredient.Type;
import ru.sharashin.tacocloud.domain.Order;
import ru.sharashin.tacocloud.domain.Taco;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

	private IngredientRepository ingredientRepository;
	private TacoRepository designRepository;


	@Autowired
	public DesignTacoController(IngredientRepository ingredientRepository, TacoRepository designRepository) {
		this.ingredientRepository = ingredientRepository;
		this.designRepository = designRepository;
	}

	@ModelAttribute(name = "order")
	public Order order() {
		return new Order();
	}

	@ModelAttribute(name = "taco")
	public Taco taco() {
		return new Taco();
	}


	@GetMapping
	public String showDesignForm(Model model) {
		populateModelWithIngredients(model);

//		model.addAttribute("taco", new Taco());

		return "design";
	}

	private void populateModelWithIngredients(Model model) {
		List<Ingredient> ingredients = (List<Ingredient>) ingredientRepository.findAll();

		Type[] types = Type.values();
		for (Type type : types) {
			List<Ingredient> filteredByType = ingredients.stream()
					.filter(ingredient -> ingredient.getType() == type)
					.collect(Collectors.toList());

			model.addAttribute(type.toString().toLowerCase(), filteredByType);
		}
	}

	@PostMapping
	public String processDesign(@Valid Taco taco, Errors errors, @ModelAttribute Order order, Model model) {
		if (errors.hasErrors()) {
			return "design";
		}
		Taco saved = designRepository.save(taco);
		order.addDesign(taco);
		log.info("Processing taco " + taco);
		return "redirect:/orders/current";
	}

}
