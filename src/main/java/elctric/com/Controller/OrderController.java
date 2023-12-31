package elctric.com.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import elctric.com.dtos.CreateOrderRequest;
import elctric.com.dtos.OrderDto;
import elctric.com.dtos.apiResponse;
import elctric.com.dtos.pagableResponse;
import elctric.com.dtos.updatorderrequest;
import elctric.com.exception.badApiRequestException;
import elctric.com.service.OrderService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //create
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequest request) throws badApiRequestException {
        OrderDto order = orderService.createOrder(request);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{orderId}")
    public ResponseEntity<apiResponse> removeOrder(@PathVariable String orderId) {
        orderService.removeOrder(orderId);
        apiResponse responseMessage = apiResponse.builder()
                .status(HttpStatus.OK)
                .message("order is removed !!")
                .sucess(true)
                .build();
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);

    }

    //get orders of the user

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersOfUser(@PathVariable String userId) {
        List<OrderDto> ordersOfUser = orderService.getOrdersOfUser(userId);
        return new ResponseEntity<>(ordersOfUser, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<pagableResponse<OrderDto>> getOrders(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "orderedDate", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir
    ) {
        pagableResponse<OrderDto> orders = orderService.getOrders(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    
    @PutMapping("/{orderid}")
    public ResponseEntity<OrderDto> updateorder(@PathVariable("orderid") String orderid,@RequestBody updatorderrequest updatorderrequest){
	
    	  OrderDto orderDto = this.orderService.updateorder(orderid, updatorderrequest);
    	  
    	  return new ResponseEntity<OrderDto>(orderDto,HttpStatus.OK);
    	
    	
   
    }


}
