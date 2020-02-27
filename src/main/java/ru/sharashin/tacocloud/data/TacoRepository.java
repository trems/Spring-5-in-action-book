package ru.sharashin.tacocloud.data;

import org.springframework.data.repository.CrudRepository;
import ru.sharashin.tacocloud.domain.Taco;

public interface TacoRepository extends CrudRepository<Taco, Long> {
}
