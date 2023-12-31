package elctric.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import elctric.com.entity.CartItem;

public interface  cartitemRepository extends JpaRepository<CartItem, Integer> {

}
