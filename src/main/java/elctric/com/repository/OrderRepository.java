package elctric.com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import elctric.com.entity.Order;
import elctric.com.entity.Users;

public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findByUser(Users user);

}
