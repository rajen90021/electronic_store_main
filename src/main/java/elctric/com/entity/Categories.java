package elctric.com.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Categories {

	
	@Id
	private String id;
	
	@Column(length = 500)
	private String title;
	
	
	
	@Column(length = 500)
	private String description;
	
	private String categoriesimage;
	
	@OneToMany(mappedBy = "categories",cascade =  CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Product> product= new ArrayList<>();
	
	
}
