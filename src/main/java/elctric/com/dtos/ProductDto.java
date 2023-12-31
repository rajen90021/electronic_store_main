package elctric.com.dtos;

import java.util.Date;

import javax.persistence.Column;

import elctric.com.entity.Categories;
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
public class ProductDto {
	
	
private String  id;
	
	
	private String title;
	
	@Column(length = 10000)
	private String discription;
	
	private int price;
	
	private int discountedprice;
	private int quantity;
	
	
	private Date addeddate;
	private boolean live ;
	private boolean stock;
	private String productImage;
	
	private CategoriesDto categories;
	
}
