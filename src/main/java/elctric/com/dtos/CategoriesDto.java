package elctric.com.dtos;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import elctric.com.entity.Categories;
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
public class CategoriesDto {
	
	@Id
	private String id;
	
	
	@NotBlank(message = "title is required")
	@Size(min = 4,message = "title is required!!")
	private String title;

//	@Column(length = 500)
	@Size(min = 4,max = 1996,message = "description is required!!")
	@NotBlank(message = "description is required")
	private String description;
	
	
	private String categoriesimage;
	
	
}
