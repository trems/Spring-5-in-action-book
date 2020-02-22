package ru.sharashin.tacocloud.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.sharashin.tacocloud.domain.Order;
import ru.sharashin.tacocloud.domain.Taco;

import java.util.HashMap;
import java.util.Map;

@Repository
public class JdbcOrderRepository implements OrderRepository {

	private SimpleJdbcInsert orderInserter;
	private SimpleJdbcInsert orderTacosInserter;
	private ObjectMapper objectMapper;

	@Autowired
	public JdbcOrderRepository(JdbcTemplate jdbc) {
		this.orderInserter = new SimpleJdbcInsert(jdbc).withTableName("Taco_Order").usingGeneratedKeyColumns("id");
		this.orderTacosInserter = new SimpleJdbcInsert(jdbc).withTableName("Taco_Order_Tacos");
		this.objectMapper = new ObjectMapper();
	}

	@Override
	public Order save(Order order) {
		long orderId = saveOrderDetails(order);
		order.setId(orderId);
		for (Taco taco : order.getTacos()) {
			saveTacoToOrder(orderId, taco);
		}
		return order;
	}

	private long saveOrderDetails(Order order) {
		@SuppressWarnings("unchecked")
		Map<String, Object> values = objectMapper.convertValue(order, Map.class);
		values.put("placedAt", order.getPlacedAt()); // переписываем значение правильной датой потому что jackson конвертит её в long
		return orderInserter.executeAndReturnKey(values).longValue();
	}

	private void saveTacoToOrder(long orderId, Taco taco) {
		Map<String, Object> values = new HashMap<>();
		values.put("tacoOrder", orderId);
		values.put("taco", taco.getId());
		orderTacosInserter.execute(values);
	}
}
