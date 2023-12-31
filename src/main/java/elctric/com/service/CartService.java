package elctric.com.service;

import org.springframework.stereotype.Service;

import elctric.com.dtos.AddItemToCartRequest;
import elctric.com.dtos.CartDto;
import elctric.com.exception.badApiRequestException;



@Service
public interface CartService {
	
	
	 //add items to cart:
    //case1: cart for user is not available: we will create the cart and then add the item
    //case2: cart available add the items to cart
    CartDto addItemToCart(String userId, AddItemToCartRequest request)throws badApiRequestException ;

    //remove item from cart:
    void removeItemFromCart(String userId,int cartItem);

    //remove all items from cart
    void clearCart(String userId);

    CartDto getCartByUser(String userId);
	   
}
