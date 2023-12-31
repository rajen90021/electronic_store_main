package elctric.com.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import elctric.com.dtos.AddItemToCartRequest;
import elctric.com.dtos.CartDto;
import elctric.com.dtos.apiResponse;
import elctric.com.exception.badApiRequestException;
import elctric.com.service.implement.CartServiceimpl;

@RestController

@RequestMapping("/carts")
public class CartControlller {

	
	  @Autowired
	    private CartServiceimpl cartService;

	    //add items to cart
	    @PostMapping("/{userId}")
	    public ResponseEntity<CartDto> addItemToCart(@PathVariable String userId, @RequestBody AddItemToCartRequest request) throws badApiRequestException {
	        CartDto cartDto = cartService.addItemToCart(userId, request);
	        return new ResponseEntity<>(cartDto, HttpStatus.OK);
	    }

	    @DeleteMapping("/{userId}/items/{itemId}")
	    public ResponseEntity<apiResponse> removeItemFromCart(@PathVariable String userId, @PathVariable int itemId) {
	        cartService.removeItemFromCart(userId, itemId);
	        apiResponse response = apiResponse.builder()
	                .message("Item is removed !!")
	                .sucess(true)
	                .status(HttpStatus.OK)
	                .build();
	        return new ResponseEntity<>(response, HttpStatus.OK);

	    }

	    //clear cart
	    @DeleteMapping("/{userId}")
	    public ResponseEntity<apiResponse> clearCart(@PathVariable String userId) {
	        cartService.clearCart(userId);
	        apiResponse response = apiResponse.builder()
	                .message("Now cart is blank !!")
	                .sucess(true)
	                .status(HttpStatus.OK)
	                .build();
	        return new ResponseEntity<>(response, HttpStatus.OK);

	    }

	    //add items to cart
	    @GetMapping("/{userId}")
	    public ResponseEntity<CartDto> getCart(@PathVariable String userId) {
	        CartDto cartDto = cartService.getCartByUser(userId);
	        return new ResponseEntity<>(cartDto, HttpStatus.OK);
	    }
}
