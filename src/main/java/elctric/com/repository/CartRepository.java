package elctric.com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import elctric.com.entity.Cart;
import elctric.com.entity.Users;

public interface CartRepository extends JpaRepository<Cart, String> {
	
	
	
	 Optional<Cart> findByUsers(Users user);

}
