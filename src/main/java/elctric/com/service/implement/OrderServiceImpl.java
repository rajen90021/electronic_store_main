package elctric.com.service.implement;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import elctric.com.dtos.CreateOrderRequest;
import elctric.com.dtos.OrderDto;
import elctric.com.dtos.ProductDto;
import elctric.com.dtos.pagableResponse;
import elctric.com.dtos.updatorderrequest;
import elctric.com.entity.Cart;
import elctric.com.entity.CartItem;
import elctric.com.entity.Order;
import elctric.com.entity.OrderItem;
import elctric.com.entity.Product;
import elctric.com.entity.Users;
import elctric.com.exception.ResourceNotFoundException;
import elctric.com.exception.badApiRequestException;
import elctric.com.repository.CartRepository;
import elctric.com.repository.OrderRepository;
import elctric.com.repository.UserRepository;
import elctric.com.service.OrderService;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CartRepository cartRepository;


    @Override
    public OrderDto createOrder(CreateOrderRequest orderDto) throws badApiRequestException {

        String userId = orderDto.getUserId();
        String cartId = orderDto.getCartId();
        //fetch user
        Users user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id !!"));

        //fetch cart
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart with given id not found on server !!"));

        List<CartItem> cartItems = cart.getItems();

        if (cartItems.size() <= 0) {
            throw new badApiRequestException("Invalid number of items in cart !!");

        }

        //other checks

        Order order = Order.builder()
                .billingName(orderDto.getBillingName())
                .billingPhone(orderDto.getBillingPhone())
                .billingAddress(orderDto.getBillingAddress())
                .orderedDate(new Date())
                .deliveredDate(null)
                .paymentStatus(orderDto.getPaymentStatus())
                .orderStatus(orderDto.getOrderStatus())
                .orderId(UUID.randomUUID().toString())
                .user(user)
                .build();

//        orderItems,amount

        AtomicReference<Integer> orderAmount = new AtomicReference<>(0);
        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
//            CartItem->OrderItem
            OrderItem orderItem = OrderItem.builder()
                    .quantity(cartItem.getQuantity())
                    .product(cartItem.getProduct())
                    .totalPrice(cartItem.getQuantity() * cartItem.getProduct().getDiscountedprice())
                    .order(order)
                    .build();

            orderAmount.set(orderAmount.get() + orderItem.getTotalPrice());
            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);
        order.setOrderAmount(orderAmount.get());

        //
        cart.getItems().clear();
        cartRepository.save(cart);
        Order savedOrder = orderRepository.save(order);
        return modelMapper.map(savedOrder, OrderDto.class);
    }

    @Override
    public void removeOrder(String orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("order is not found !!"));
        orderRepository.delete(order);

    }

    @Override
    public List<OrderDto> getOrdersOfUser(String userId) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found !!"));
        List<Order> orders = orderRepository.findByUser(user);
        List<OrderDto> orderDtos = orders.stream().map(order -> modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
        return orderDtos;
    }


    public pagableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = Sort.by(sortBy);

        if (sortDir.equalsIgnoreCase("desc")) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        Page<Order> pageOfOrder = this.orderRepository.findAll(pageRequest);

        List<Order> listOfContent = pageOfOrder.getContent();

        List<OrderDto> orderDtoList = listOfContent.stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());

        pagableResponse<OrderDto> response = new pagableResponse<>();

        response.setContect(orderDtoList);

        response.setPagenumber(pageOfOrder.getNumber());
        response.setPagesize(pageOfOrder.getSize());
        response.setTotalelement(pageOfOrder.getTotalElements());
        response.setTotalpage(pageOfOrder.getTotalPages());
        response.setLastpage(pageOfOrder.isLast());

        return response;
    }

	@Override
	public OrderDto updateorder(String orderid, updatorderrequest request) {
	
		 Order order = orderRepository.findById(orderid).orElseThrow(() -> new ResourceNotFoundException("Order ID not found"));

		    order.setBillingAddress(request.getBillingAddress());
		    order.setBillingName(request.getBillingName());
		    order.setBillingPhone(request.getBillingPhone());
		    order.setDeliveredDate(request.getDeliveredDate());
		    order.setOrderStatus(request.getOrderStatus());
		    order.setPaymentStatus(request.getPaymentStatus());

		    Order updatedOrder = orderRepository.save(order);

		    return modelMapper.map(updatedOrder, OrderDto.class);
		    
		  
	}

}











