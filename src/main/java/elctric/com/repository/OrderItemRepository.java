package elctric.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import elctric.com.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer>
{
	
}