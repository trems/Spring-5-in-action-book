package ru.sharashin.tacocloud.data;

import org.springframework.data.repository.CrudRepository;
import ru.sharashin.tacocloud.domain.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
