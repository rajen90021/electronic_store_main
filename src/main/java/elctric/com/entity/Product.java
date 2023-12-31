package elctric.com.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ManyToAny;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Product {

	
	@Id
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
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "categores_id")
	private Categories categories;
	

	
	
}
