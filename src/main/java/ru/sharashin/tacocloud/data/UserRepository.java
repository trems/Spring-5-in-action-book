package ru.sharashin.tacocloud.data;

import org.springframework.data.repository.CrudRepository;
import ru.sharashin.tacocloud.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {

	User findUserByUsername(String username);

}
