package ru.sharashin.tacocloud.data;

import ru.sharashin.tacocloud.domain.Order;

public interface OrderRepository {

	Order save(Order order);
}
