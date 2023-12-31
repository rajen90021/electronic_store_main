package elctric.com.dtos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import elctric.com.entity.Users;
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
public class CartDto {
	

    private String cartId;
    private Date createdAt;
    private Users users; 
    private List<CartItemDto> items = new ArrayList<>();
}
