package elctric.com.dtos;



import java.util.Date;

import javax.persistence.Id;

import elctric.com.entity.Cart;
import elctric.com.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto {
	

    private int cartItemId;
    private ProductDto product;
    private int quantity;
    private int totalPrice;
	
	
	
	
	
	
}
