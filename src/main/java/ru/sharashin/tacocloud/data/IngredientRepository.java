package ru.sharashin.tacocloud.data;

import ru.sharashin.tacocloud.domain.Ingredient;

public interface IngredientRepository {

	Iterable<Ingredient> findAll();

	Ingredient findOndById(String id);

	Ingredient save(Ingredient ingredient);

}
