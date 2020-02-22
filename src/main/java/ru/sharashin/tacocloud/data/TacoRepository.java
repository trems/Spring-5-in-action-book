package ru.sharashin.tacocloud.data;

import ru.sharashin.tacocloud.domain.Taco;

public interface TacoRepository {

	Taco save(Taco taco);
}
