package elctric.com.service;

import java.util.List;

import elctric.com.dtos.CreateOrderRequest;
import elctric.com.dtos.OrderDto;
import elctric.com.dtos.pagableResponse;
import elctric.com.dtos.updatorderrequest;
import elctric.com.exception.badApiRequestException;

public interface OrderService {

    //create order
    OrderDto createOrder(CreateOrderRequest orderDto) throws badApiRequestException;

    //remove order
    void removeOrder(String orderId);

    //get orders of user
    List<OrderDto> getOrdersOfUser(String userId);
    
    
    
    public OrderDto updateorder(String orderid,updatorderrequest request);

    //get orders
    pagableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String dirction);

    //order methods(logic) related to order

}