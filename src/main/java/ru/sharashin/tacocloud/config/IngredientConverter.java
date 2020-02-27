package ru.sharashin.tacocloud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.sharashin.tacocloud.data.IngredientRepository;
import ru.sharashin.tacocloud.domain.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class IngredientConverter implements Converter<String[], List<Ingredient>> {

	private static Map<String, Ingredient> cache = new ConcurrentHashMap<>();

	private IngredientRepository ingredientRepository;

	@Autowired
	public IngredientConverter(IngredientRepository ingredientRepository) {
		this.ingredientRepository = ingredientRepository;
		initCache();
	}

	private void initCache() {
		if (cache.isEmpty()) {
			ingredientRepository.findAll().forEach(ingredient -> cache.put(ingredient.getId(), ingredient));
		}
	}

	private Ingredient getIngredient(String id) {
		Ingredient result = cache.get(id);
		if (result == null) {
			Optional<Ingredient> ingredientFromDb = ingredientRepository.findById(id);
			result = ingredientFromDb.orElseThrow();
			cache.put(result.getId(), result);
		}
		return result;
	}

	@Override
	public List<Ingredient> convert(String[] ingredientIds) {
		List<Ingredient> result = new ArrayList<>();
		for (String id : ingredientIds) {
			result.add(getIngredient(id));
		}
		return result;
	}
}
