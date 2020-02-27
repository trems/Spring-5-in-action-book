package ru.sharashin.tacocloud.data;

import org.springframework.data.repository.CrudRepository;
import ru.sharashin.tacocloud.domain.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
